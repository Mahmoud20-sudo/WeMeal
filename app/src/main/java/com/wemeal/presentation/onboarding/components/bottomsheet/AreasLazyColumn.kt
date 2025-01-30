package com.wemeal.presentation.onboarding.components.bottomsheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.libraries.maps.model.LatLng
import com.wemeal.data.model.Resource
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.intro.blue200
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase

import com.wemeal.presentation.onboarding.components.search.EmptyStateView

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun AreasLazyColumn(
    listType: String,
    state: LazyListState,
    navController: NavHostController,
    viewModel: OnBoardingViewModel,
    _id: MutableState<String> = mutableStateOf(""),
    searchText: MutableState<String> = mutableStateOf(""),
    onDismiss: ((latLng: LatLng) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    val liveDataModel by when (listType) {
        CITIES -> viewModel.citiesLiveData.observeAsState()
        AREAS -> viewModel.areasLiveData.observeAsState()
        else -> {
            viewModel.subAreasLiveData.observeAsState()
        }
    }

    val itemsList = when (listType) {
        CITIES -> viewModel.citiesList.value
        AREAS -> viewModel.areasList.value
        else -> {
            viewModel.subAreasList.value
        }
    }

    val page: MutableState<Int> = when (listType) {
        CITIES -> viewModel.citiesPage
        AREAS -> viewModel.areasPage
        else -> viewModel.subAreasPage
    }

    LaunchedEffect(true) {
        viewModel.resetData(listType)
        callApi(viewModel, listType, searchText.value, _id)
    }

    when (liveDataModel) {
        is Resource.Success -> {
            loading = false
            liveDataModel?.data?.result?.let {
                when (listType) {
                    CITIES -> viewModel.appendCities(it)
                    AREAS -> {
                        viewModel.appendAreas(it)
                    }
                    else -> {
                        viewModel.appendSubAreas(it)
                    }
                }
            }
        }
        is Resource.Error -> {
            loading = false
            liveDataModel?.message?.let { context.shortToast(it) }
        }
        else -> loading = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = loading && page.value == 1,
            modifier = Modifier.align(Alignment.Center)
        ) {
            CircularProgressIndicator(color = blue200)
        }
        AnimatedVisibility(
            visible = itemsList.isNotEmpty(),
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier, state,
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 26.dp)
            ) {
                itemsIndexed(itemsList) { index, item ->

                    viewModel.onChangeRecipeScrollPosition(listType, index)
                    if ((index + 1) >= (page.value * PAGE_SIZE) && !loading) {
                        viewModel.nextPage(listType, _id.value, searchText.value, page)
                    }

                    AreaItem(
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClickLabel = "NAVIGATE",
                            onClick = {
                                searchText.value = ""
                                when {
                                    listType.contains(CITIES) -> {
                                        navController.navigate("${AREAS}/${item._id}/${item.name?.en}")
                                        context.activity()?.logEvent(
                                            CustomEvent.USER_SELECT_CITY_FROM_LIST,
                                            EventCase.ATTEMPT
                                        )
                                    }
                                    listType.contains(SUB_AREAS) -> {
                                        item.location?.coordinates.let {
                                            onDismiss?.invoke(
                                                LatLng(
                                                    it?.get(1) ?: 0.0,
                                                    it?.get(0) ?: 0.0
                                                )
                                            )
                                        }
                                        context.activity()?.logEvent(
                                            CustomEvent.USER_SELECT_SUBAREA_FROM_LIST,
                                            EventCase.SUCCESS
                                        )
                                    }
                                    else -> {
                                        //if (item.hasSubareas == true) {
                                        navController.navigate("${SUB_AREAS}/${item._id}/${item.areaOnlyName?.en}")
                                        context.activity()?.logEvent(
                                            CustomEvent.USER_SELECT_AREA_FROM_LIST,
                                            EventCase.ATTEMPT
                                        )
//                                        } else {
//                                            item.location?.coordinates.let {
//                                                onDismiss?.invoke(
//                                                    LatLng(
//                                                        it?.get(1) ?: 0.0,
//                                                        it?.get(0) ?: 0.0
//                                                    )
//                                                )
//                                            }
//                                        }
                                    }
                                }
                            }
                        ), result = item)
                }
            }
        }
        AnimatedVisibility(
            visible = !loading && itemsList.isEmpty(),
            modifier = Modifier.fillMaxSize()
        ) {
            EmptyStateView(
                querySearch = searchText.value,
                imageId = com.wemeal.R.drawable.ic_location_no_result,
                isLocationSearch = true
            ) {
                onClick?.invoke()
            }
        }
    }
}

//private fun addCities(viewModel: OnBoardingViewModel, items: List<Result>) {
//    for (item in items) {
//        if (!viewModel.citiesList.value.contains(item))
//            viewModel.citiesList.value.add(item)
//    }
//}
//
//private fun addAreas(viewModel: OnBoardingViewModel, items: List<Result>) {
//    for (item in items) {
//        if (!viewModel.areasList.value.contains(item))
//            viewModel.areasList.value.add(item)
//    }
//}

//private fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
//    clear()
//    addAll(newList)
//}


//    val listItems: ArrayList<Result> = ArrayList()
//    val lazyMovieItems: LazyPagingItems<Result> = viewModel.movies.collectAsLazyPagingItems()


//    waitingWithDelay(2000) {
//        isListEmpty.value = !loading && page.value == 1
//    }

//                        itemsList = viewModel.citiesList.value

//                        addCities(viewModel, it)
//viewModel.citiesPageNumber++


//                        addAreas(viewModel, it)
//                        viewModel.areasPageNumber =
//                            if (viewModel.areasList.isEmpty()) 1 else viewModel.areasPageNumber++


//                    if (index == itemsList.lastIndex && liveDataModel?.data?.result?.isNotEmpty() == true) {
//                        context.shortToast("AAAAA ${page}")
//                    }
//callApi(viewModel, listType, searchText.value, cityId)

//    if (listType != CITIES) {
//        viewModel.areasPage.value = 1
//        viewModel.areasList.value.clear()
//    }
