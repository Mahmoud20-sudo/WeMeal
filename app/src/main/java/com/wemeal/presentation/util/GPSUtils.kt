package com.wemeal.presentation.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.libraries.maps.model.LatLng
import com.permissionx.guolindev.PermissionMediator
import com.permissionx.guolindev.PermissionX
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import java.lang.ref.WeakReference
import io.nlopez.smartlocation.SmartLocation
import android.content.Context.LOCATION_SERVICE
import com.wemeal.presentation.util.events.*

//Singleton
class GPSUtils private constructor() {

    private fun getCurrentLocation(context: Context, callBack: (LatLng?) -> Unit) {
//        val locationControl: WeakReference<SmartLocation.LocationControl> = WeakReference(SmartLocation.with(context).location())
//        locationControl.get()?.oneFix()?.start { location ->
//            callBack(LatLng(location.latitude, location.longitude))
//            stopLocListener(context = context)
//        }
        getLastKnownLocation(context, callBack = callBack)
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(context: Context, callBack: (LatLng?) -> Unit) {
        val mLocationManager =
            context.getSystemService(LOCATION_SERVICE) as LocationManager
        val providers: List<String> = mLocationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l: Location = mLocationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                // Found best last known location: %s", l);
                bestLocation = l
            }
        }
        callBack(
            if (bestLocation != null) LatLng(
                bestLocation.latitude,
                bestLocation.longitude
            ) else null
        )
    }

    fun requestPermission(
        context: Context,
        callBack: (LatLng?) -> Unit,
        isFromLocationButton: Boolean = false
    ) {
        context.activity()?.logEvent(CustomEvent.USER_ALLOW_LOCATION_ACCESS, EventCase.ATTEMPT)
        val permissionX: WeakReference<PermissionMediator> =
            WeakReference(PermissionX.init(context.activity()))
        permissionX.get()?.permissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
            ?.onExplainRequestReason { _, _ ->
            }
            ?.request { allGranted, _, _ ->
                if (allGranted) {
                    context.activity()
                        ?.logEvent(CustomEvent.USER_ALLOW_LOCATION_ACCESS, EventCase.SUCCESS)

                    if (isFromLocationButton)
                    //only when user change current location from current location button
                        context.activity()?.logEvent(
                            CustomEvent.USER_SELECT_CURRENT_LOCATION, EventCase.SUCCESS
                        )

                    getCurrentLocation(context, callBack)
                } else {
                    context.activity()
                        ?.logEvent(CustomEvent.USER_ALLOW_LOCATION_ACCESS, EventCase.CANCEL)
                    callBack(null)
                }
            }
    }

    fun getLocation(
        context: Context,
        callBack: (LatLng?) -> Unit,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) {
        context.activity()?.logEvent(CustomEvent.USER_SELECT_CURRENT_LOCATION, EventCase.ATTEMPT)

        val weakContext: WeakReference<Context> = WeakReference(context)

        val mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(2000)
            .setFastestInterval(1000)

        val settingsBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)
        settingsBuilder.setAlwaysShow(true)

        val result =
            LocationServices.getSettingsClient(weakContext.get()!!)
                .checkLocationSettings(settingsBuilder.build())
        result.addOnCompleteListener { task ->

            //getting the status code from exception
            try {
                val taskResult = task.getResult(ApiException::class.java)
                if (taskResult.locationSettingsStates.isGpsUsable) {
                    requestPermission(
                        context = weakContext.get()!!,
                        callBack = callBack,
                        isFromLocationButton = true
                    )
                }
            } catch (ex: ApiException) {
                context.activity()?.logEvent(
                    CustomEvent.USER_SELECT_CURRENT_LOCATION, EventCase.FAILURE
                )

                when (ex.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        val resolvableApiException = ex as ResolvableApiException
                        launcher.launch(
                            IntentSenderRequest.Builder(resolvableApiException.resolution).build()
                        )

                    } catch (e: IntentSender.SendIntentException) {
                        e.message?.let { Log.e("WeMeal", it) }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        Log.i("WeMeal", UNAVAILABLE)
                    }
                }
            }
        }
    }

    private fun stopLocListener(context: Context) {
        SmartLocation.with(context).location().stop()
    }

    companion object {
        val instance = GPSUtils()
    }

    fun isGPSActive(context: Context): Boolean {
        return (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )
    }
}
//        SmartLocation.with(context).location()
//            .oneFix()
//            .start {
//                callBack(LatLng(it.latitude, it.longitude))
//                stopLocListener(context = context)
//            }

//            ?.onForwardToSettings { scope, deniedList ->
//                scope.showForwardToSettingsDialog(deniedList,
//                    context.getString(R.string.permission_dialog_message), context.getString(R.string.ok), context.getString(R.string.cancel))
//            }
