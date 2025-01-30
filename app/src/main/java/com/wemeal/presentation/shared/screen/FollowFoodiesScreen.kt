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
import com.wemeal.presentation.extensions.logMultipleEvents
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.onboarding.components.foody.FoodyItem
import com.wemeal.presentation.onboarding.components.header.Header
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.util.LOADING
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import com.wemeal.presentation.util.foodiesList

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewFollowFoodiesScreen() {
    FollowFoodiesScreen(
        page = 3,
        viewModel = null
    ) { }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalUnitApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FollowFoodiesScreen(
    viewModel: OnBoardingViewModel?,
    page: Int = -1,
    isFromFeed: Boolean = false,
    onConfirmClick: () -> Unit = {}
) {
    val count = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val loading = remember { mutableStateOf(false) }

    val foodiesLiveData by viewModel?.foodiesLiveData!!.observeAsState()
    when (foodiesLiveData) {
        is Resource.Success -> {
            loading.value = false
            viewModel?.foodiesList?.value = foodiesLiveData?.data!!.result!!
        }
        is Resource.Error -> {
            loading.value = false
            foodiesLiveData?.message?.let { context.shortToast(it) }
        }
        else -> loading.value = true
    }

    val followFoodieLiveData by viewModel?.followLiveData!!.observeAsState()
    val unFollowFoodieLiveData by viewModel?.unFollowLiveData!!.observeAsState()

    LaunchedEffect(followFoodieLiveData) {
        when (followFoodieLiveData) {
            is Resource.Success -> {
                context.activity()
                    ?.logEvent(
                        CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD_ONBOARDING,
                        EventCase.SUCCESS
                    )
//                followFoodieLiveData?.data?.result?.success?.en?.let { context.shortToast(it) }
            }
            is Resource.Error -> {
                viewModel?.foodiesList?.value?.single { s -> s.id == viewModel.foodieId.value }?.isFollowing =
                    false
                //TODO() use the new provided logDna function
                context.activity()?.logEvent(
                    CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD_ONBOARDING,
                    EventCase.FAILURE,
                    message = followFoodieLiveData?.message
                )
                followFoodieLiveData?.message?.let { context.shortToast(it) }
            }
            else -> Log.i(LOADING, LOADING)
        }
    }

    LaunchedEffect(unFollowFoodieLiveData) {
        when (unFollowFoodieLiveData) {
            is Resource.Success -> {
                context.activity()
                    ?.logEvent(
                        CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD_ONBOARDING,
                        EventCase.SUCCESS
                    )
//                unFollowFoodieLiveData?.data?.result?.success?.en?.let { context.shortToast(it) }
            }
            is Resource.Error -> {
                viewModel?.foodiesList?.value?.single { s -> s.id == viewModel.foodieId.value }?.isFollowing =
                    true
                //TODO() use the new provided logDna function
                context.activity()?.logEvent(
                    CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD_ONBOARDING,
                    EventCase.FAILURE,
                    message = followFoodieLiveData?.message
                )
                unFollowFoodieLiveData?.message?.let { context.shortToast(it) }
            }
            else -> Log.i(LOADING, LOADING)
        }
    }

    LaunchedEffect(true) {
        viewModel?.getSuggestedFoodies()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AnimatedVisibility(visible = !isFromFeed) {
            Header(
                page = page,
                onConfirmClick = onConfirmClick,
                isSearching = remember { mutableStateOf(false) },
                searchResult = null,
                querySearch = remember { mutableStateOf("") },
                count = count,
                viewModel = viewModel
            )
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                top = 10.dp,
                start = 5.dp,
                bottom = 10.dp,
                end = 5.dp
            )
        ) {
            items(if (viewModel?.foodiesList?.value.isNullOrEmpty()) 10 else viewModel?.foodiesList?.value!!.size) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FoodyItem(
                        foodieModel = if (viewModel?.foodiesList?.value.isNullOrEmpty()) null else
                            viewModel?.foodiesList!!.value[index],
                        modifier = Modifier
                            .padding(start = 5.dp, end = 4.5.dp, bottom = 10.dp),
                        isShimmerEffect = loading.value
                    ) { isFollowed ->
                        count.value = if (isFollowed) ++count.value else --count.value
                        viewModel?.foodieId?.value =
                            viewModel?.foodiesList?.value?.get(index)?.id ?: ""

                        when (isFollowed) {
                            true -> viewModel?.followFoodie()
                            else -> viewModel?.unFollowFoodie()
                        }

                        if (!isFromFeed) //from on boarding
                            context.activity()?.logEvent(
                                if (isFollowed)
                                    CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD_ONBOARDING
                                else
                                    CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD_ONBOARDING,
                                EventCase.ATTEMPT
                            )//add success and failure events when integarating with backend
                        else
                            context.logMultipleEvents(
                                if (isFollowed)
                                    CustomEvent.USER_FOLLOW_FOODIE_FROM_FOODIE_ACTIVITY_LIST
                                else
                                    CustomEvent.USER_UNFOLLOW_FOODIE_FROM_FOODIE_ACTIVITY_LIST,
                                foodyModel = foodiesList[index],
                                                     )
                    }
                }
            }
        }
    }
}
