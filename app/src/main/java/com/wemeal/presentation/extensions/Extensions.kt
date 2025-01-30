package com.wemeal.presentation.extensions

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.text.ClipboardManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Velocity
import androidx.core.app.ShareCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.wemeal.R
import com.wemeal.data.model.FoodyModel
import com.wemeal.presentation.BaseActivity
import com.wemeal.presentation.util.events.CustomEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.system.exitProcess

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
val File.sizeInGb get() = sizeInMb / 1024
val File.sizeInTb get() = sizeInGb / 1024

fun FragmentActivity.signOut() {
    //clear google session
    GoogleSignIn.getClient(
        this,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
    ).signOut()

    FirebaseAuth.getInstance().signOut()
    LoginManager.getInstance().logOut()
    //clear facebook sessions
//    GraphRequest(
//        AccessToken.getCurrentAccessToken(),
//        "/me/permissions/", null, HttpMethod.DELETE
//    ) {
//        AccessToken.setCurrentAccessToken(null)
//        LoginManager.getInstance().logOut()
//    }.executeAsync()

    finish()
//    SocialActivityArgs().launch(this)
}

fun BaseActivity.closeApp() {
    CoroutineScope(IO).launch {
        moveTaskToBack(true)
        delay(100)
        exitProcess(-1)
    }
}

fun waitingWithDelay(delayDuration: Long, callBack: () -> Unit) {
    CoroutineScope(IO).launch {
        delay(delayDuration)
        callBack.invoke()
    }
}

fun getAddress(context: Context, latLng: LatLng): String {
    try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (address.isNotEmpty())
            return address[0].getAddressLine(0)

    } catch (e: IOException) {
        e.message?.let {
            Log.e("TAG", it)
            return it
        }
    }
    return ""
}

@SuppressLint("SimpleDateFormat")
fun format(format: String, date: String?): String {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.timeZone = TimeZone.getTimeZone("UTC");//2020-11-15T15:48:04.952Z
        val originDate = dateFormat.parse(date)
        val displayedDate = SimpleDateFormat(format)

        displayedDate.format(originDate).toString()
    } catch (e: Exception) {
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.getDateText(date: String): String {//2022-02-06T00:29:24.272Z
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val displayedDate = SimpleDateFormat("dd MMMM")
    val displayedDateWithYear = SimpleDateFormat("dd MMMM yyyy")

    var resultText = ""

    try {
        val oldDate = dateFormat.parse(date)
        val currentDate = Date()
        val diff = currentDate.time - oldDate!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        val dateWithoutYear = displayedDate.format(oldDate).toString()
        val dateWithYear = displayedDateWithYear.format(oldDate).toString()

        when (days) {
            0L -> {
                resultText = when {
                    minutes == 0L -> if (seconds == 0L) getString(R.string.just_now) else "$seconds ${
                        getString(
                            R.string.seconds_ago
                        )
                    }"
                    hours > 0 -> {
                        "$hours hours ago"
                    }
                    else -> {
                        "$minutes ${getString(R.string.minutes_ago)}"
                    }
                }
            }
            in 1L..6L -> resultText = "$days ${getString(R.string.days_ago)}"
            else -> {
                resultText =
                    if (days < 365) dateWithoutYear else dateWithYear
            }
        }

//        if (oldDate.before(currentDate)) {
//            Log.e("oldDate", "is previous date")
//            Log.e(
//                "Difference: ", " seconds: " + seconds + " minutes: " + minutes
//                        + " hours: " + hours + " days: " + days
//            )
//        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return resultText
}

fun hideKeyBoard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

infix fun Context.shareExternal(link: String) = ShareCompat.IntentBuilder(this)
    .setType("text/plain")
    .setChooserTitle(getString(R.string.share_via))
    .setText(link)
    .startChooser();

infix fun Context.copyToClipboard(link: String) {
    val sdk = Build.VERSION.SDK_INT
    if (sdk < Build.VERSION_CODES.HONEYCOMB) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        clipboard!!.text = link
    } else {
        val clipboard =
            getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager?
        val clip = ClipData.newPlainText(link, link)
        clipboard!!.setPrimaryClip(clip)
    }
    shortToast(getString(R.string.copied))
}

infix fun Context.shortToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.notInternetToast() =
    Toast.makeText(this, getString(R.string.internet_not_working), Toast.LENGTH_LONG).show()

fun isLocationsEqual(loc1: LatLng, loc2: LatLng): Boolean {
    val distance = FloatArray(1)
    Location.distanceBetween(loc1.latitude, loc1.longitude, loc2.latitude, loc2.longitude, distance)
    return when {
        distance[0] < 2.0 -> {
            true
        }
        else -> false
    }
}

@SuppressLint("UnnecessaryComposedModifier")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

private val VerticalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(x = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(x = 0f)
}

private val HorizontalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

fun Modifier.disabledVerticalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(VerticalScrollConsumer) else this

fun Modifier.disabledHorizontalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(HorizontalScrollConsumer) else this

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.setGestureModifier(
    context: Context,
    event: CustomEvent,
    foodyModel: FoodyModel
): Modifier = composed {
    Modifier
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()

                val (x, y) = dragAmount
                when {
                    x > 0 -> {/* right */
                        context.logMultipleEvents(
                            event,
                            foodyModel = foodyModel
                        )
                    }
                    x < 0 -> { /* left */
                        context.logMultipleEvents(
                            event,
                            foodyModel = foodyModel
                        )
                    }
                }
                when {
                    y > 0 -> { /* down */
                    }
                    y < 0 -> { /* up */
                    }
                }
            }
        }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

fun isEven(n: Int): Boolean {
    return n % 2 == 0;
}

fun isOdd(n: Int): Boolean {
    return abs(n % 2) == 1;
}

@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}

tailrec fun Context?.activity(): BaseActivity? = this as? BaseActivity
    ?: (this as? ContextWrapper)?.baseContext?.activity()

fun setBackStackData(navController: NavController, id: String) {
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.apply {
//            set(IS_REPORTED, true)
            set("report-id", id)
        }
}

fun clearBackStackData(navController: NavController) {
    navController.currentBackStackEntry
        ?.savedStateHandle
        ?.apply {
            set("report-id", null)
//            remove<String>("report-index")
        }
}
