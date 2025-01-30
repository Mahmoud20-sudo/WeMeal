package com.wemeal.presentation.onboarding.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.wemeal.data.model.Resource
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.onboarding.components.header.Header
import com.wemeal.presentation.onboarding.components.brand.BrandItem
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.util.LOADING
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import com.wemeal.presentation.util.resturantsList

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewOnBoardingTwoScreen() {
    OnBoardingTwoScreen(viewModel = null, page = 2) { }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalUnitApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnBoardingTwoScreen(
    viewModel: OnBoardingViewModel?,
    page: Int = -1,
    isFromFeed: Boolean = false,
    onClick: () -> Unit = {}
) {
    val count = remember { mutableStateOf(0) }
    val context = LocalContext.current

    val loading = remember { mutableStateOf(true) }

    val brandsLiveData by viewModel?.brandsLiveData!!.observeAsState()
    when (brandsLiveData) {
        is Resource.Success -> {
            loading.value = false
            viewModel?.brandsList?.value = brandsLiveData?.data!!.result!!
        }
        is Resource.Error -> {
            loading.value = false
            brandsLiveData?.message?.let { context.shortToast(it) }
        }
        else -> Log.i(LOADING, LOADING)
    }

    val followBrandLiveData by viewModel?.followLiveData!!.observeAsState()
    val unFollowBrandLiveData by viewModel?.unFollowLiveData!!.observeAsState()

    LaunchedEffect(followBrandLiveData) {
        when (followBrandLiveData) {
            is Resource.Success -> {
                loading.value = false
                context.activity()
                    ?.logEvent(
                        CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD_ONBOARDING,
                        EventCase.SUCCESS
                    )
//                followBrandLiveData?.data?.result?.success?.en?.let { context.shortToast(it) }
            }
            is Resource.Error -> {
                loading.value = false
                viewModel?.brandsList?.value?.single { s -> s.id == viewModel.brandId.value }?.isFollowing =
                    false
                context.activity()?.logEvent(
                    CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD_ONBOARDING,
                    EventCase.FAILURE,
                    message = followBrandLiveData?.message
                )
                followBrandLiveData?.message?.let { context.shortToast(it) }
            }
            else ->
                Log.e("LOADING", "LOADING")
        }
    }

    LaunchedEffect(unFollowBrandLiveData) {
        when (unFollowBrandLiveData) {
            is Resource.Success -> {
                loading.value = false
                context.activity()
                    ?.logEvent(
                        CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD_ONBOARDING,
                        EventCase.SUCCESS
                    )
//                unFollowBrandLiveData?.data?.result?.success?.en?.let { context.shortToast(it) }
            }
            is Resource.Error -> {
                loading.value = false
                viewModel?.brandsList?.value?.single { s -> s.id == viewModel.brandId.value }?.isFollowing =
                    true
                context.activity()?.logEvent(
                    CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD_ONBOARDING,
                    EventCase.FAILURE,
                    message = unFollowBrandLiveData?.message
                )
                unFollowBrandLiveData?.message?.let { context.shortToast(it) }
            }
            else ->
                Log.i(LOADING, LOADING)
        }
    }

    LaunchedEffect(true) {
        viewModel?.getSuggestedBrands()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AnimatedVisibility(visible = !isFromFeed) {
            Header(
                page = page,
                onConfirmClick = onClick,
                isSearching = remember { mutableStateOf(false) },
                searchResult = null,
                querySearch = remember { mutableStateOf("") },
                count = count,
                viewModel = viewModel
            )
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 10.dp, start = 5.dp, bottom = 10.dp, end = 5.dp)
        ) {
            items(if (viewModel?.brandsList?.value.isNullOrEmpty()) 10 else viewModel?.brandsList?.value!!.size) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BrandItem(
                        brandModel = if (viewModel?.brandsList?.value.isNullOrEmpty()) null else
                            viewModel?.brandsList!!.value[index],
                        modifier = Modifier
                            .padding(start = 5.dp, end = 4.5.dp, bottom = 10.dp),
                        isShimmerEffect = loading.value
                    ) { isFollowed ->
                        count.value = if (isFollowed) ++count.value else --count.value
                        viewModel?.brandId?.value =
                            viewModel?.brandsList?.value?.get(index)?.id ?: ""

                        when (isFollowed) {
                            true -> viewModel?.followBrand()
                            else -> viewModel?.unFollowBrand()
                        }

                        context.activity()?.logEvent(
                            if (isFollowed)
                                CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD_ONBOARDING
                            else
                                CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD_ONBOARDING,
                            EventCase.ATTEMPT
                        )
                    }
                }
            }
        }
    }
}

private fun checkCount(): Int {
    var count = 0
    for (resturant in resturantsList) {
        if (resturant.isFollowed.value)
            count++
    }
    return count
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}