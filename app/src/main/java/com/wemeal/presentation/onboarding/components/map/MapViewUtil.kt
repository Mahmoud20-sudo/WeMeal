package com.wemeal.presentation.onboarding.components.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.maps.android.ktx.awaitMap
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.CameraUpdateFactory
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.nearest.NearestModel
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.onboarding.components.text.MapInfoText
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.GPSUtils
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.LOADING
import com.wemeal.presentation.util.RC_LOCATION_NO_RESULT_FOUND
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

lateinit var googleMap: GoogleMap
val gpsUtils = GPSUtils.instance
//lateinit var nearestAreas: State<Resource<NearestModel>?>

@SuppressLint("MissingPermission")
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun MapViewUtil(
    map: MapView,
    location: LatLng,
    isLoading: MutableState<Boolean>,
    onBoardingViewModel: OnBoardingViewModel,
    zoom: MutableState<Float>,
    currentlySelectedJobId: Int
) {
    val coroutineScope = rememberCoroutineScope()

    val context: WeakReference<Context> = WeakReference(LocalContext.current)
    var isVisible by remember { mutableStateOf(true) }
    var isPermitted by remember { mutableStateOf(false) }

    val viewModel : WeakReference<OnBoardingViewModel> = WeakReference(onBoardingViewModel)

    val nearestAreas by viewModel.get()?.nearestLocationLiveData!!.observeAsState()

    //callback for updating map with current/default location from GPSUtils class
    val callback: (LatLng?) -> Unit = { currentLoc ->
        isPermitted = true
        zoom.value = 15f
        currentLoc?.let {
            viewModel.get()?.selectedLocation = it
        }
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(currentLoc ?: location, 15f)
        )
    }

    when (nearestAreas) {
        is Resource.Success -> {
            isLoading.value = false
            viewModel.get()?.locationModel?.value = nearestAreas?.data!!
        }
        is Resource.Error -> {
//            isLoading.value = false
            nearestAreas?.message?.let { context.get().activity()?.shortToast(it) }
        }
        else -> Log.i(LOADING, LOADING)
    }

    LaunchedEffect(viewModel.get()?.selectedLocation) {
        viewModel.get()?.selectedLocation?.let {
            isLoading.value = true
            viewModel.get()?.getNearestLocation(it)
        }
    }

    //register launcher for location setting access callback
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK)//success
                gpsUtils.requestPermission(context.get()!!, callback)
            else//fail (navigate to default location)
                callback(null)
        }

    LaunchedEffect(currentlySelectedJobId) {
        //user click on use current location from cities/areas popup
        if (currentlySelectedJobId >= RC_LOCATION_NO_RESULT_FOUND)
            gpsUtils.getLocation(context = context.get()!!, callback, launcher)
    }

    //google map is now in jetpack compose
    //https://github.com/googlemaps/android-maps-compose
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { _ ->
                map.apply {
                    coroutineScope.launch {
                        googleMap = awaitMap()

                        googleMap.isMyLocationEnabled = false
                        gpsUtils.getLocation(context = context.get()!!, callback, launcher)
                        googleMap.setOnCameraMoveListener {
                        }
                        googleMap.setOnCameraIdleListener {
                            //still initializing map
                            if (googleMap.cameraPosition.target.latitude == 0.0)
                                return@setOnCameraIdleListener

                            val cameraPosition = googleMap.cameraPosition
                            zoom.value = googleMap.cameraPosition.zoom
                            isPermitted = true
                            if (!isLocationsEqual(
                                    viewModel.get()?.selectedLocation!!,
                                    cameraPosition.target
                                )
                            )
                                viewModel.get()?.selectedLocation = cameraPosition.target

//                            context.get().activity()?.logEvent(
//                                CustomEvent.USER_SELECT_LOCATION_BY_MAP, EventCase.SUCCESS //EventCase.CANCEL
//                            )
                        }
                        delay(1500)
                        isVisible = false
                    }
                }
            },
            update = {
                if (::googleMap.isInitialized && isPermitted) {
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(location, zoom.value)
                    )
                }
            }
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 36.3.dp, end = 36.3.dp),
            visible = isVisible
        ) {
            MapInfoText()
        }
        Image(
            painterResource(id = R.drawable.ic_map_location),
            contentDescription = "CURRENT LOCATION",
            modifier = Modifier
                .align(Alignment.Center)
                .width(36.dp)
                .height(30.dp)
        )
        ClickableImage(
            drawableId = R.drawable.ic_detect_location,
            imageActions = ImageActions.DETECT,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 172.dp)
                .size(44.dp)
        ) {
            gpsUtils.getLocation(context = context.get()!!, callback, launcher)
        }
    }
}

//@Composable
//private fun getLocation(viewModel: OnBoardingViewModel): Resource<NearestModel>? {
//    val nearestLocation by viewModel.nearestLocation.observeAsState()
//    //val loading = remember { mutableStateOf(true) }
//    viewModel.getNearestLocation(selectedLocation)
//    return nearestLocation
//
////    when (nearestLocation) {
////        is Resource.Success -> {
////            Log.e("AA", "AA")
////        }
////        is Resource.Error -> {
////            nearestLocation?.message?.let { Log.e("AA", it) }
////        }
////        else -> loading.value = true
////    }
//}

//    if (isCameraMoved) {
//        viewModel.getNearestLocation(viewModel.selectedLocation)
//        isCameraMoved = false
//    }