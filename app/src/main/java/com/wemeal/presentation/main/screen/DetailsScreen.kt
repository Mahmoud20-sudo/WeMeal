package com.wemeal.presentation.main.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.data.model.Resource
import com.wemeal.presentation.main.components.details.AddCommentCard
import com.wemeal.data.model.Screen
import com.wemeal.presentation.extensions.clearBackStackData
import com.wemeal.presentation.extensions.loggingPostAction
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.main.components.shimmer.ShimmerItem
import com.wemeal.presentation.main.components.typeone.PostItem
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase

@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewDetailsScreen() {
    DetailsScreen(
        rememberNavController(),
        viewModel = null
    )
}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun DetailsScreen(navController: NavController, viewModel: MainViewModel?) {
    val context = LocalContext.current
    val loading = remember { mutableStateOf(true) }

    val getPostObserver = viewModel?.getPostLiveData?.observeAsState()

    when (getPostObserver?.value) {
        is Resource.Success -> {
            loading.value = false
        }
        is Resource.Error -> {
            loading.value = false
        }
        else -> loading.value = true
    }

    val reportScreenResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("report-id")?.observeAsState()

    reportScreenResult?.value?.let { index ->
        //Read the result
        //foodyModel.isConfirmShowing.value = true
        viewModel?.postDetails?.activity?.isConfirmShowing = mutableStateOf(true)
        clearBackStackData(navController)
    }

    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize(), visible = loading.value
    ) {
        ShimmerItem()
    }

    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize(), visible = !loading.value
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
//        if(foodyModel.isTypeThreeFeed && foodyModel.promotionReason.isNotEmpty())
//            PromotedPostItem(
//                foodyModel = foodyModel,
//                onPostClick = { clickedIndex ->
//                    navigateToDetails(navController, clickedIndex)
//                },
//                onProfileClick = { id ->
//                    navigateToProfile(navController, id)
//                }, onImageClick = { i, imgs ->
//                    navigateToImagesPager(context, navController, imgs, i)
//                }
//            )
//        else if (foodyModel.sharedPostIndex > -1)
//            SharedPostItem(
//                foodyModel = foodyModel, modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .clickable(onClick = { //handle onClick
////                    navigateToDetails(navController, index)
//                    }),
//                onPostClick = { clickedIndex ->
//                    navigateToDetails(navController, clickedIndex)
//                }, onDeleteClick = {
//                    foodiesList[it].isDeleted.value =
//                        !foodiesList[it].isDeleted.value
//                }, onReportClick = {
////                    navigateToReport(navController)
//                },
//                onProfileClick = {
//                    navigateToProfile(navController, foodyModel.id)
//                }
//            )
//        else

            PostItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                isDetails = true,
                model = viewModel?.postDetails,
                onLikeClick = {
                    when (viewModel?.postDetails?.activity?.isLikedMutable?.value) {
                        false -> {
                            viewModel.unLikePost(viewModel.postDetails?.activity?._id ?: "")
                        }
                        else -> {
                            viewModel?.likePost(viewModel.postDetails?.activity?._id ?: "")
                        }
                    }
                },
                onImageClick = { index: Int, imgs: List<String> ->
                    navigateToImagesPager(
                        context = context,
                        navController = navController,
                        index = index,
                        viewModel = viewModel
                    )
                },
                onDeleteClick = { isConfirmed ->
                    when (isConfirmed) {
                        false -> {
                            loggingPostAction(
                                context = context,
                                model = viewModel?.postDetails,
                                eventName = CustomEvent.USER_CLICK_DELETE_POST,
                                eventCase = EventCase.SUCCESS
                            )
                            viewModel?.postDetails?.activity?.isConfirmShowing?.value = true
                        }
                        else -> {
                            viewModel?.postDetails?.activity?.isUserDeleting?.value = true
                            viewModel?.deletePost(viewModel.postDetails?.activity?._id ?: "")
                        }
                    }
                },
                onFollowClick = {
                    when {
                        viewModel?.postDetails?.type == FeedType.PROMOTED.name -> {
                            viewModel.postDetails?.activity?.isFollowed?.value =
                                !viewModel.postDetails?.activity?.isFollowed?.value!!
                        }
                        viewModel?.postDetails?.activity?.isUnFollowed?.value == true -> {
                            viewModel.postDetails?.activity?.isUnFollowed?.value = false
                            viewModel.followPost(viewModel.postDetails?.activity?._id ?: "")
                        }
                        else -> {
                            viewModel?.postDetails?.activity?.isUnFollowed?.value = true
                            viewModel?.unFollowPost(viewModel.postDetails?.activity?._id ?: "")
                        }
                    }
                },
                onReportClick = { isConfirmed ->
                    when (isConfirmed) {
                        false -> {
                            loggingPostAction(
                                context = context,
                                model = viewModel?.postDetails,
                                eventName = when (viewModel?.postDetails?.activity?.user?.role) {
                                    UserType.BRAND.name -> CustomEvent.USER_LIKE_RESTAURANT_POST
                                    UserType.FOODIE.name -> CustomEvent.USER_LIKE_FOODIE_POST
                                    else -> CustomEvent.USER_LIKE_THEIR_POST
                                },
                                eventCase = EventCase.SUCCESS
                            )
                            navigateToReport(navController, viewModel?.postDetails?.activity?._id)
                        }
                        else -> viewModel?.reportPost(viewModel?.postDetails?.activity?._id ?: "")
                    }
                },
                onProfileClick = {
//                    navigateToProfile(
//                        navController,
//                        foodiesList[index - 1].id
//                    ) //for user profile page
                },
//            onOtherFoodiesClick = { contentType ->
//                navigateToAllFoodies(
//                    context,
//                    navController,
//                    index = index,
//                    contentType
//                ) //for all foodies page
//            }
            )
            Spacer(modifier = Modifier.weight(1f))
            AddCommentCard(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

private fun navigateToImagesPager(
    context: Context,
    navController: NavController,
    index: Int,
    viewModel: MainViewModel?
) {
    navController.navigate(
        Screen.Pager.route
            .replace("{index}", index.toString())
    )

    loggingPostAction(
        context = context,
        model = viewModel?.postDetails,
        eventName = CustomEvent.USER_CLICK_IMAGE_TO_VIEW_FULL_SCREEN,
        eventCase = EventCase.SUCCESS
    )
//    context.logMultipleEvents(
//        CustomEvent.USER_CLICK_IMAGE_TO_VIEW_FULL_SCREEN,
//        foodiesList[index]
//    )
}
