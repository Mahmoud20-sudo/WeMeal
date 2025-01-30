package com.wemeal.presentation.onboarding.components.map

import android.content.Context
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.wemeal.R
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.util.GPSUtils
import java.lang.ref.WeakReference

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun GoogleMapSnapshot(
    modifier: Modifier,
    isLoading: MutableState<Boolean>,
    location: LatLng,
    viewModel: OnBoardingViewModel,
    zoom: MutableState<Float>,
    currentlySelectedJobId: Int
) {
    val mapView = rememberMapViewWithLifecycle()
    MapViewUtil(
        map = mapView,
        location,
        isLoading,
        viewModel,
        zoom,
        currentlySelectedJobId
    )
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context: WeakReference<Context> = WeakReference(LocalContext.current.applicationContext)
    val mapView: WeakReference<MapView> = WeakReference(remember {
        MapView(context.get()).apply {
            id = R.id.map
        }
    })

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, mapView.get()) {
        // Make MapView follow the current lifecycle
        val lifecycleObserver = getMapLifecycleObserver(mapView.get()!!)
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView.get()!!
}

private fun getMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }
