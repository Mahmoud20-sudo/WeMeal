package com.wemeal.presentation.onboarding.components.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.wemeal.data.model.user.PlaceDataModel
import com.wemeal.presentation.extensions.getPlaceDetails
import com.wemeal.presentation.intro.blue200
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.util.placesSearchTimeoutDurationMillis
import com.wemeal.presentation.util.repeatTimes
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

private var lastQuery = ""

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@OptIn(ExperimentalUnitApi::class)
@Composable
fun SearchView(
    searchResult: SnapshotStateList<PlaceDataModel>,
    querySearch: MutableState<String>,
    zoomLevel: MutableState<Float>,
    loadingDetails: MutableState<Boolean>,
    viewModel: OnBoardingViewModel,
    isSearching: MutableState<Boolean>
) {

    val state = rememberLazyListState()
    val context = LocalContext.current
    val isResultEmpty = remember { mutableStateOf(false) }

    val onSelect: (Place?) -> Unit = { place ->
        place?.latLng?.let {
            viewModel.selectedLocation = LatLng(it.latitude, it.longitude)
        }
        zoomLevel.value = 15f
        loadingDetails.value = false
        searchResult.clear()
    }

    LaunchedEffect(true) {
        repeat(repeatTimes) {
            delay(placesSearchTimeoutDurationMillis)
            //waiting results for 30 seconds before showing empty-state view
            if (searchResult.isEmpty() && querySearch.value.isNotEmpty())
                isResultEmpty.value = true
        }
    }

    if (!lastQuery.equals(querySearch.value, true)) {
        lastQuery = querySearch.value
        isResultEmpty.value = false
    }

    Box {
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = isResultEmpty.value
        ) {
            EmptyStateView(querySearch.value)
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = searchResult.isEmpty() && querySearch.value.isNotEmpty() && !isResultEmpty.value
        ) {
            CircularProgressIndicator(
                color = blue200
            )
        }
        LazyColumn(
            Modifier.fillMaxSize(), state,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(searchResult) { searchItem ->
                SearchResultItem(
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClickLabel = "Set Result",
                        onClick = {
                            isSearching.value = false
                            loadingDetails.value = true
                            CoroutineScope(IO).launch {
                                getPlaceDetails(context, searchItem) {
                                    onSelect(it)
                                }
                            }
                        }
                    ),
                    placeDataModel = searchItem)
            }
        }
    }
}