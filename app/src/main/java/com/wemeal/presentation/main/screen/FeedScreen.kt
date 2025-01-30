package com.wemeal.presentation.main.screen

import android.content.Context
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wemeal.data.model.Resource
import com.wemeal.data.model.Screen
import com.wemeal.data.model.main.feed.Result
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.intro.lightGray
import com.wemeal.presentation.main.components.createpost.PostCreatedLoadingRow
import com.wemeal.presentation.main.components.deletion.DeletionBox
import com.wemeal.presentation.main.components.posts.*
import com.wemeal.presentation.main.components.shimmer.ShimmerItem
import com.wemeal.presentation.main.components.typeone.PostItem
import com.wemeal.presentation.main.components.violation.ViolationBox
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.CustomEventParams
import com.wemeal.presentation.util.events.EventCase
import kotlin.random.Random

@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewFeedScreen() {
    FeedScreen(navController = rememberNavController(), viewModel = null)
}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: MainViewModel?,
) {
    val context = LocalContext.current
    val feedObserver = viewModel?.getFeedLiveData?.observeAsState()
    val createPostObserver = viewModel?.createPostLiveData?.observeAsState()

    val listState = rememberLazyListState()
    var isViolated by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val reportScreenResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("report-id")?.observeAsState()


    LaunchedEffect(createPostObserver?.value) {
        isViolated = createPostObserver?.value?.data?.error != null
        Log.e("isViolated", isViolated.toString())
    }

    LaunchedEffect(Unit) {
        keyboardController?.hide()
    }

    reportScreenResult?.value?.let { _id ->
        //Read the result
        viewModel?.feedListSnapShot?.single { s -> s.activity._id == _id }?.activity?.apply {
            isConfirmShowing = mutableStateOf(true)
        }
        clearBackStackData(navController)
    }

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray.copy(alpha = 0.5f)),
        state = rememberSwipeRefreshState(isRefreshing = feedObserver?.value is Resource.Loading && viewModel?.feedObject?.value?.result?.isNotEmpty() == true),
        onRefresh = {
            refreshFeed(viewModel = viewModel)
        },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                // Pass the SwipeRefreshState + trigger through
                state = state,
                refreshTriggerDistance = trigger,
                // Enable the scale animation
                scale = true,
                // Change the color and shape
                backgroundColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small,
            )
        }
    ) {
//
        val threshold = 5
        val lastIndex = viewModel?.feedListSnapShot?.lastIndex ?: 0

        LazyColumn(state = listState) {
            item {
                CreatePostRow {
                    navigateToCreatePost(context = context, navController = navController)
                }

                if (createPostObserver?.value != null && !isViolated) {
                    PostCreatedLoadingRow(
                        viewModel = viewModel
                    ) {
                        context.activity()?.logEvent(
                            event = CustomEvent.USER_CLICK_TRY_AGAIN_CREATE_POST,
                            EventCase.SUCCESS,
                            bundle = bundleOf(
                                CustomEventParams.OBJECT_TYPE.name to viewModel?.getObjectType(),
                            )
                        )
                        viewModel.createPost()
                    }
                }

                AnimatedVisibility(
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 0)
                    ),
                    exit = slideOutHorizontally(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 0)
                    ),
                    visible = isViolated
                ) {
                    ViolationBox(
                        onDismissClick = {
                            isViolated = false
                            viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_X_CLOSE_POST_MODEREATED_FEEDBACK)
                        },
                        rulesClick = {
                            viewModel?.isBottomSheetExpanded?.value = true
                            viewModel?.isPosting?.value = false
                            isViolated = false
                            viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_COMMUNITY_RULES_FROM_POST_MODERATED_FEEDBACK)
                        }) {
                        viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_CREATE_NEW_POST_FROM_POST_MODERATED_FEEDBACK)
                        isViolated = false
                        navController.navigate(Screen.CreatePost.route)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                )
            }

            items(if (feedObserver?.value == null || feedObserver.value is Resource.Loading) 5 else 0) {
                ShimmerItem()
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                )
            }

            items(
                count = viewModel?.feedListSnapShot?.size?.plus(1) ?: 0,
                key = { index ->
                    viewModel?.feedListSnapShot?.getOrNull(index)?.activity?._id
                        ?: "PLACEHOLDER${index}"
                }
            ) { index ->

                val item = viewModel?.feedListSnapShot?.getOrNull(index)

                if (item?.activity?._id?.isEmpty() == false) {
                    viewModel.setItemMutableValues(item)

                    if (index + threshold >= lastIndex && viewModel.afterId.value.isNotEmpty()) {
                        SideEffect {
                            Log.d("###", "$index $lastIndex")
                            viewModel.getFeed()
                            return@SideEffect
                        }
                    }

                    SetPostItem(
                        navController = navController,
                        feedModel = item,
                        viewModel = viewModel
                    )
                }

                AnimatedVisibility(
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 0)
                    ),
                    exit = slideOutHorizontally(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 0)
                    ),
                    visible = index == lastIndex + 1 && viewModel?.afterId?.value?.isNotEmpty() == true
                ) {
                    ShimmerItem()
                }
//                }

//                itemsIndexed(items = viewModel?.feedObject?.value?.result?.toList() ?: listOf(),
//                    key = { _, item -> item.activity._id }) { index, item ->
//
//                    setItemMutableValues(item)
//
//                    if (index + threshold >= lastIndex
//                        && viewModel?.afterId?.value?.isNullOrEmpty() == false
//                    ) {
//                        SideEffect {
//                            Log.d("###", "$index $lastIndex")
//                            viewModel?.getFeed()
//                            return@SideEffect
//                        }
//                    }
//
//                    SetPostItem(
//                        navController = navController,
//                        feedModel = item,
//                        viewModel = viewModel
//                    )
//
//
//                    Spacer(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(3.dp)
//                    )
//                }
            }

//        Column(
//            modifier = Modifier
//                .background(lightGray.copy(alpha = 0.5f))
//                .fillMaxSize()
//                .verticalScroll(scrollState)
//        ) {
//
//            CreatePostRow {
//                navigateToCreatePost(context = context, navController = navController)
//            }
//
//            if (!viewModel?.postText?.value.isNullOrEmpty()
//                || viewModel?.imagesList?.isNotEmpty() == true
//                && viewModel?.createPostLiveData?.value != null
//            ) {
//                PostCreatedLoadingRow(
//                    viewModel = viewModel
//                ) {
//                    context.activity()?.logEvent(
//                        event = CustomEvent.USER_CLICK_TRY_AGAIN_CREATE_POST,
//                        EventCase.SUCCESS
//                    )
//                    viewModel?.createPost()
//                }
//            }
//
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(3.dp)
//            )
//
//            if (feedObserver?.value == null || feedObserver.value is Resource.Loading) {
//                Column {
//                    repeat(5) {
//                        ShimmerItem()
//                        Spacer(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(3.dp)
//                        )
//                    }
//                }
//            }
//
//            viewModel?.feedObject?.value?.result?.forEachIndexed { index, item ->
//                setItemMutableValues(item)
//
////                if ((index + 1) >= viewModel.feedObject?.value?.result!!.size && viewModel.getFeedLiveData.value !is Resource.Loading) {
////                    viewModel.getFeed()
////                }
//
//                SetPostItem(
//                    navController = navController,
//                    feedModel = item,
//                    viewModel = viewModel
//                )
//
////                        AnimatedVisibility(
////                            visible = foodiesList[index - 1].isUnFollowed.value
////                        ) {
////                            UnfollowedPostBox(foodiesList[index - 1])
////                        }
////                        AnimatedVisibility(
////                            visible = !foodiesList[index - 1].isDeleted.value && !foodiesList[index - 1].isUnFollowed.value,
////                            enter = fadeIn(
////                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
////                                initialAlpha = 0.4f
////                            ),
////                            exit = fadeOut(
////                                // Overwrites the default animation with tween
////                                animationSpec = tween(durationMillis = 250)
////                            )
////                        ) {
//
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(3.dp)
//                )
//                //}
//            }
//        }
        }
    }
}

private fun refreshFeed(viewModel: MainViewModel?) {
    viewModel?.feedListSnapShot?.clear()
    viewModel?.afterId?.value = ""
    viewModel?.getFeed()
}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalUnitApi
@Composable
private fun SetPostItem(
    navController: NavController,
    feedModel: Result?,
    viewModel: MainViewModel?
) {
    val context = LocalContext.current

    when (feedModel?.type?.uppercase()) {
        FeedType.POST.name -> {
            when {
                feedModel.activity.isUnFollowed?.value == true -> {
                    UnfollowedPostBox(feedModel.activity) {
                        feedModel.activity.isUnFollowed?.value = false
                        viewModel?.followPost(it?._id ?: "")
                    }
                }
                feedModel.activity.isPostDeleted?.value == true -> {
                    DeletionBox {
                        refreshFeed(viewModel = viewModel)
                    }
                }
                else -> AnimatedVisibility(
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 500)
                    ),
                    visible = feedModel.activity.isUserDeleting?.value == false
                ) {
                    PostItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable(onClick = {
                                viewModel?.getPost(feedModel.activity._id) //handle onClick
                                navigateToDetails(navController)
                            }),
                        model = feedModel,
                        onLikeClick = {
                            when (feedModel.activity.isLikedMutable?.value) {
                                false -> viewModel?.unLikePost(feedModel.activity._id)
                                else -> viewModel?.likePost(feedModel.activity._id)
                            }
                        },
                        onImageClick = { _: Int, _: List<String> ->
                            viewModel?.getPost(feedModel.activity._id) //handle onClick
                            navigateToDetails(navController)
                        },
                        onDeleteClick = { isConfirmed ->
                            when (isConfirmed) {
                                false -> {
                                    loggingPostAction(
                                        context = context,
                                        model = viewModel?.postPressed,
                                        eventName = CustomEvent.USER_CLICK_DELETE_POST,
                                        eventCase = EventCase.SUCCESS
                                    )
                                    feedModel.activity.isConfirmShowing?.value = true
                                }
                                else -> {
                                    feedModel.activity.isUserDeleting?.value = true
                                    viewModel?.deletePost(feedModel.activity._id)
                                }
                            }
                        },
                        onFollowClick = {
                            when {
                                feedModel.type == FeedType.PROMOTED.name -> {
                                    feedModel.activity.isFollowed.value =
                                        !feedModel.activity.isFollowed.value
                                }
                                feedModel.activity.isUnFollowed?.value == true -> {
                                    feedModel.activity.isUnFollowed?.value = false
                                    viewModel?.followPost(feedModel.activity._id)
                                }
                                else -> {
                                    feedModel.activity.isUnFollowed?.value = true
                                    viewModel?.unFollowPost(feedModel.activity._id)
                                }
                            }
                        },
                        onReportClick = { isConfirmed ->
                            when (isConfirmed) {
                                false -> {
                                    loggingPostAction(
                                        context = context,
                                        model = viewModel?.postPressed,
                                        eventName = CustomEvent.USER_CLICK_REPORT_POST,
                                        eventCase = EventCase.SUCCESS
                                    )
                                    navigateToReport(navController, feedModel.activity._id)
                                }
                                else -> viewModel?.reportPost(feedModel.activity._id)
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
                }
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (feedModel?.activity?.isUserDeleting?.value) {
                    true -> 0.dp
                    else -> 3.dp
                }
            )
    )
}

//ALL THIS LOGIC IS TEMP AND WILL BE CHANGED WITH INTGERATION WITH BE
//  when {
//        foodiesList[index - 1].isTypeFourFeed -> {
//            RecommendedItem(
//                foodyModel = foodiesList[index - 1],
//                onSeeAllClick = {
//                    if (foodiesList[index - 1].isRestaurantActivity) {
//                        navigateToOtherRestaurants(navController, index)
////                        context.logMultiEvents(
////                            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_FOLLOWING_RESTAURANT,
////                            foodiesList[index - 1]
////                        )
//                    } else {
//                        navigateToOtherFoodies(navController, index)
////                        context.logMultiEvents(
////                            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_FOLLOWING_FOODIE,
////                            foodiesList[index - 1]
////                        )
//                    }
//
//                }
//            )
//        }
//        foodiesList[index - 1].isTypeThreeFeed && foodiesList[index - 1].promotionReason.isNotEmpty() -> {//promoted posts with reason
//            PromotedPostItem(
//                itemIndex = index - 1,
//                foodyModel = foodiesList[index - 1],
//                onPostClick = {
//                    navigateToDetails(navController, index)
//                },
//                onProfileClick = { id ->
//                    navigateToProfile(navController, id)
//                }, onImageClick = { i, imgs ->
//                    navigateToDetails(navController, index)
//                }
//            )
//        }
//        foodiesList[index - 1].isTypeTwoFeed -> {
//            AnimatedVisibility(
//                visible = (foodiesList[index - 1].isRestaurantActivity
//                        || foodiesList[index - 1].isFoodieActivity)
//                        && !foodiesList[index - 1].isFollowingActivity
//                        && foodiesList[index - 1].followedUserImg > 0
//            ) {
//                ActivityPostItem(foodyModel = foodiesList[index - 1],
//                    onPostClick = {
//                        navigateToDetails(navController, index)
//                    },
//                    onProfileClick = { id ->
//                        navigateToProfile(navController, id)
//                    }, onOtherFoodiesClick =
//                    { contentType ->
//                        navigateToAllFoodies(
//                            context,
//                            navController,
//                            index,
//                            contentType
//                        ) //for all foodies page
//                    }, onImageClick = { _, _ ->
//                        navigateToDetails(navController, index)
//                    })
//            }
//            AnimatedVisibility(
//                visible = foodiesList[index - 1].category != null
//                        && !foodiesList[index - 1].isRestaurantActivity
//                        && !foodiesList[index - 1].isFoodieActivity
//            ) {
//                TaggedObjectPostItem(
//                    foodyModel = foodiesList[index - 1],
//                    onOtherFoodiesClick = { contentType ->
//                        navigateToAllFoodies(context, navController, index = index, contentType)
//                    },
//                    onProfileClick = {
//                        navigateToProfile(navController, 1)
//                    })
//            }
//            AnimatedVisibility(
//                visible = !foodiesList[index - 1].isManyUser && foodiesList[index - 1].category == null
//                        && foodiesList[index - 1].isFollowingActivity
//            ) {
//                FollowedOneFoodyItem(foodyModel = foodiesList[index - 1], onOtherFoodiesClick = {
//                    if (foodiesList[index - 1].isRestaurantActivity) {
//                        navigateToOtherRestaurants(navController, index)
//                        context.logMultipleEvents(
//                            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_FOLLOWING_RESTAURANT,
//                            foodiesList[index - 1]
//                        )
//                    } else {
//                        navigateToOtherFoodies(navController, index)
//                        context.logMultipleEvents(
//                            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_FOLLOWING_FOODIE,
//                            foodiesList[index - 1]
//                        )
//                    }
//
//                }, onProfileClick = {
//                    navigateToProfile(navController, 1)
//                })
//            }
//            AnimatedVisibility(
//                visible = foodiesList[index - 1].isManyUser
//                        && foodiesList[index - 1].category == null
//                        && foodiesList[index - 1].isFollowingActivity
//            ) {
//                FollowedManyFoodyItem(
//                    foodyModel = foodiesList[index - 1],
//                    onOtherFoodiesClick = { sellAll ->
//                        if (foodiesList[index - 1].isRestaurantActivity) {
//                            navigateToOtherRestaurants(navController, index)
//                            //if clicks on see all USER_CLICK_SEE_ALL_FOLLOWED_RESTAURANTS_LIST
//                            context.logMultipleEvents(
//                                if (sellAll) CustomEvent.USER_CLICK_SEE_ALL_FOLLOWED_RESTAURANTS_LIST
//                                else CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_FOLLOWING_RESTAURANT,
//                                foodiesList[index - 1]
//                            )
//                        } else {
//                            navigateToOtherFoodies(navController, index)
//                            //if clicks on see all USER_CLICK_SEE_ALL_FOLLOWED_FOODIES_LIST
//                            context.logMultipleEvents(
//                                if (sellAll) CustomEvent.USER_CLICK_SEE_ALL_FOLLOWED_FOODIES_LIST
//                                else CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_FOLLOWING_FOODIE,
//                                foodiesList[index - 1]
//                            )
//                        }
//                    },
//                    onProfileClick = {
//                        navigateToProfile(navController, 1)
//                    })
//            }
//            AnimatedVisibility(
//                visible = foodiesList[index - 1].isFollowingActivity &&
//                        foodiesList[index - 1].isRestaurantActivity //temp condition for showing restaurnat item
//            ) {
//                Fried Chicken(
//                    foodyModel = foodiesList[index - 1],
//                    onOtherFoodiesClick = {
//                        navigateToOtherRestaurants(navController)
//                    },
//                    onProfileClick = {
//                        navigateToProfile(navController, 1)
//                    })
//            }
//        foodiesList[index - 1].sharedPostIndex > -1 ->
//            SharedPostItem(
//                index,
//                foodiesList[index - 1], Modifier
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
//                    navigateToReport(navController, index)
//                },
//                onProfileClick = {
//                    navigateToProfile(navController, foodiesList[index - 1].id)
//                }
//            )
//else ->

//            onOtherFoodiesClick = { contentType ->
//                navigateToAllFoodies(
//                    context,
//                    navController,
//                    index = index,
//                    contentType
//                ) //for all foodies page
//            }


fun navigateToDetails(navController: NavController) {
    navController.navigate(Screen.Details.route)
}

fun navigateToReport(navController: NavController, id: String?) {
    navController.navigate("${Screen.Report.route}/${id}")
}

fun navigateToProfile(navController: NavController, id: Int) {
    navController.navigate("${Screen.Profile.route}/${id}")
}

fun navigateToOtherFoodies(navController: NavController, index: Int) {
    navController.navigate("${Screen.FollowedFoodies.route}/${index}")
}

fun navigateToOtherRestaurants(navController: NavController, index: Int) {
    navController.navigate("${Screen.ALLRestaurants.route}/${index}")
}

fun navigateToCreatePost(context: Context, navController: NavController) {
    context
        .activity()
        ?.logEvent(
            event = CustomEvent.USER_CLICK_CREATE_POST_ACTION_BOX, EventCase.SUCCESS
        )
    navController.navigate(Screen.CreatePost.route)
}

fun navigateToAllFoodies(
    context: Context,
    navController: NavController,
    index: Int,
    type: Int = POSTED_ABOUT_TYPE
) {
    when (type) {
        POSTED_ABOUT_TYPE -> context.logMultipleEvents(
            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_POSTING_ACTIVITY,
            foodiesList[index - 1]
        )
        SHARED_TYPE -> context.logMultipleEvents(
            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_SHARING_ACTIVITY,
            foodiesList[index - 1]
        )
        ORDERED_FROM_TYPE -> context.logMultipleEvents(
            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_ORDERING_ACTIVITY,
            foodiesList[index - 1]
        )
        COMMENTED_ON_TYPE -> context.logMultipleEvents(
            CustomEvent.USER_CLICK_FOODIES_LIST_GROUP_COMMENTING_ACTIVITY,
            foodiesList[index - 1]
        )
    }

    navController.navigate("${Screen.ALLFoodies.route}/${index - 1}/${type}")
}

@Composable
fun Lifecycle.observeAsSate(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsSate.addObserver(observer)
        onDispose {
            this@observeAsSate.removeObserver(observer)
        }
    }
    return state
}

//    val coroutineScope = rememberCoroutineScope()
//    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
//
//    val lifeCycleState = LocalLifecycleOwner.current?.lifecycle?.observeAsSate()
//    val lifecycleState = lifeCycleState?.value

