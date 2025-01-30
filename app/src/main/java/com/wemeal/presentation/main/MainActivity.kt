package com.wemeal.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.opensooq.supernova.gligar.GligarPicker
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.data.model.Screen
import com.wemeal.data.model.main.tagged.brands.Result
import com.wemeal.presentation.BaseActivity
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.intro.lightBlack
import com.wemeal.presentation.main.components.createpost.BottomSheetContent
import com.wemeal.presentation.main.components.details.DetailsTopBar
import com.wemeal.presentation.main.components.navigation.*
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.main.viewmodel.MainViewModelFactory
import com.wemeal.presentation.shared.BottomSheetLayout
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.CustomEventParams
import com.wemeal.presentation.util.events.EventCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@ExperimentalAnimationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory
    lateinit var viewModel: MainViewModel

    private var currentRoute: String? = ""
    private lateinit var navController: NavHostController
    var index by Delegates.notNull<Int>()
    private val sharedPreferencesManager = SharedPreferencesManager.instance

    private val isDialogShowing = mutableStateOf(false)
    var isMentionText = mutableStateOf(false)
    var isDoneEnabled = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        changeStatusBarIconsColor(
            color = R.color.light_grey,
            shouldChangeStatusBarTintToDark = true
        )
        initialization()
        initObservers()
        setContent()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this, factory = factory)[MainViewModel::class.java]
        viewModel.getFeed()
    }

    private fun initObservers() {
        viewModel.postImageLiveData.observe(this) { response ->
            if (response is Resource.Error) {
                response.message?.let { shortToast(it) }
            }
        }

        viewModel.searchUsersLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    isMentionText.value =
                        isMentionText.value && !response.data?.result.isNullOrEmpty()
                    response.data?.result?.toMutableList()?.let { viewModel.usersList?.addAll(it) }
                }
                else -> {
                    isMentionText.value = false
                    response.message?.let { shortToast(it) }
                }
            }
        }

        viewModel.searchBrandsLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    viewModel.brandsList.value = response.data?.result as MutableList<Result>
                    viewModel.noSearchResults.value = viewModel.brandsList.value.isEmpty() == true
                }
                else -> {
                    response.message?.let { shortToast(it) }
                }
            }
        }

        viewModel.searchOffersLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    viewModel.offersList.value =
                        response.data?.result as MutableList<com.wemeal.data.model.main.tagged.offers.Result>
                    viewModel.noSearchResults.value = viewModel.offersList.value.isEmpty() == true
                }
                else -> {
                    response.message?.let { shortToast(it) }
                }
            }
        }

        viewModel.searchMealsLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    viewModel.mealsList.value =
                        response.data?.result as MutableList<com.wemeal.data.model.main.tagged.products.Result>
                    viewModel.noSearchResults.value = viewModel.mealsList.value.isEmpty() == true
                }
                else -> {
                    response.message?.let { shortToast(it) }
                }
            }
        }

        viewModel.searchOrdersLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    viewModel.ordersList.value =
                        response.data?.result as MutableList<com.wemeal.data.model.main.tagged.orders.Result>
                    viewModel.noSearchResults.value = viewModel.ordersList.value.isEmpty() == true
                }
                else -> {
                    response.message?.let { shortToast(it) }
                }
            }
        }

        viewModel.createPostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    shortToast(getString(R.string.posted))
                    sharedPreferencesManager.postsCount = 1
                    viewModel.postText.value = ""
                    viewModel.resetCreatePostData()
                    response.data?.result?.let {
                        viewModel.feedListSnapShot.add(0, it)
                    }
                    logEvent(
                        CustomEvent.USER_CLICK_POST,
                        EventCase.SUCCESS,
                        bundle = bundleOf(
                            CustomEventParams.OBJECT_TYPE.name to viewModel.getObjectType(),
                            CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel.isTagged.value) 1 else 0),
                            CustomEventParams.POST_WITH_TEXT.name to (if (viewModel.postText.value.isNotEmpty()) 1 else 0),
                            CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel.imagesList.isNotEmpty()) 1 else 0)
                        )
                    )
                }
                else -> {
                    response.message?.let { shortToast(it) }
                    logEvent(
                        CustomEvent.USER_CLICK_POST,
                        EventCase.FAILURE,
                        message = response.message,
                        bundle = bundleOf(
                            CustomEventParams.OBJECT_TYPE.name to viewModel.getObjectType(),
                            CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel.isTagged.value) 1 else 0),
                            CustomEventParams.POST_WITH_TEXT.name to (if (viewModel.postText.value.isNotEmpty()) 1 else 0),
                            CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel.imagesList.isNotEmpty()) 1 else 0)
                        )
                    )
                }
            }
        }

        viewModel.getGalleryLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    viewModel.brandGallery?.value = response.data
                }
                else -> {
                    response.message?.let { shortToast(it) }
                }
            }
        }

        viewModel.getPostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> viewModel.isDetailsLoading.value = true
                is Resource.Success -> {
                    response.data?.let {
                        viewModel.setItemMutableValues(it.result)
                        viewModel.postDetails = it.result
                    }
                    viewModel.isDetailsLoading.value = false
                }
                else -> {
                    response.message?.let {
                        shortToast(it)
                        viewModel.updateFeedList(it)
                    }
                    navController.navigateUp()
                }
            }
        }

        viewModel.getFeedLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    response.data?.let {
                        viewModel.feedListSnapShot.addAll(it.result)
                    }
                    viewModel.afterId.value = response.data?.after_id ?: ""
                }
                else -> {
                    response.message?.let { shortToast(it) }
                }
            }
        }

        viewModel.likePostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> loggingPostAction(
                    context = this,
                    model = viewModel.postPressed,
                    eventName = when (viewModel.postPressed?.activity?.user?.role) {
                        UserType.BRAND.name -> CustomEvent.USER_LIKE_RESTAURANT_POST
                        UserType.FOODIE.name -> CustomEvent.USER_LIKE_FOODIE_POST
                        else -> CustomEvent.USER_LIKE_THEIR_POST
                    },
                    eventCase = EventCase.ATTEMPT
                )
                is Resource.Success -> loggingPostAction(
                    context = this,
                    model = viewModel.postPressed,
                    eventName = when (viewModel.postPressed?.activity?.user?.role) {
                        UserType.BRAND.name -> CustomEvent.USER_LIKE_RESTAURANT_POST
                        UserType.FOODIE.name -> CustomEvent.USER_LIKE_FOODIE_POST
                        else -> CustomEvent.USER_LIKE_THEIR_POST
                    },
                    eventCase = EventCase.SUCCESS
                )
                else -> {
                    response.message?.let {
                        shortToast(it)
                        viewModel.updateFeedList(it).apply {
                            isLikedMutable?.value = false
                            likeCountMutable?.value = likeCountMutable?.value?.minus(1)
                        }
                    }
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = when (viewModel.postPressed?.activity?.user?.role) {
                            UserType.BRAND.name -> CustomEvent.USER_LIKE_RESTAURANT_POST
                            UserType.FOODIE.name -> CustomEvent.USER_LIKE_FOODIE_POST
                            else -> CustomEvent.USER_LIKE_THEIR_POST
                        },
                        eventCase = EventCase.FAILURE
                    )
                }
            }
        }

        viewModel.unLikePostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> loggingPostAction(
                    context = this,
                    model = viewModel.postPressed,
                    eventName = when (viewModel.postPressed?.activity?.user?.role) {
                        UserType.BRAND.name -> CustomEvent.USER_UNLIKE_RESTAURANT_POST
                        UserType.FOODIE.name -> CustomEvent.USER_UNLIKE_FOODIE_POST
                        else -> CustomEvent.USER_UNLIKE_THEIR_POST
                    },
                    eventCase = EventCase.ATTEMPT
                )
                is Resource.Success -> loggingPostAction(
                    context = this,
                    model = viewModel.postPressed,
                    eventName = when (viewModel.postPressed?.activity?.user?.role) {
                        UserType.BRAND.name -> CustomEvent.USER_UNLIKE_RESTAURANT_POST
                        UserType.FOODIE.name -> CustomEvent.USER_UNLIKE_FOODIE_POST
                        else -> CustomEvent.USER_UNLIKE_THEIR_POST
                    },
                    eventCase = EventCase.SUCCESS
                )
                else -> {
                    response.message?.let {
                        shortToast(it)
                        viewModel.updateFeedList(it).apply {
                            isLikedMutable?.value = true
                            likeCountMutable?.value = likeCountMutable?.value?.plus(1)
                        }
                    }
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = when (viewModel.postPressed?.activity?.user?.role) {
                            UserType.BRAND.name -> CustomEvent.USER_UNLIKE_RESTAURANT_POST
                            UserType.FOODIE.name -> CustomEvent.USER_UNLIKE_FOODIE_POST
                            else -> CustomEvent.USER_UNLIKE_THEIR_POST
                        },
                        eventCase = EventCase.FAILURE
                    )
                }
            }
        }

        viewModel.reportPostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> loggingPostAction(
                    context = this,
                    model = viewModel.postPressed,
                    eventName = CustomEvent.USER_CONFIRM_SUBMIT_REPORT_POST,
                    eventCase = EventCase.ATTEMPT
                )
                is Resource.Success -> {
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = CustomEvent.USER_CONFIRM_SUBMIT_REPORT_POST,
                        eventCase = EventCase.SUCCESS
                    )
                    shortToast(getString(R.string.report_submitted))
                }
                else -> {
                    response.message?.let {
                        shortToast(it)
                        viewModel.updateFeedList(it)
                    }
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = CustomEvent.USER_CONFIRM_SUBMIT_REPORT_POST,
                        eventCase = EventCase.FAILURE
                    )
                }
            }
        }

        viewModel.followPostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = when (viewModel.postPressed?.activity?.user?.role) {
                            UserType.BRAND.name -> CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD
                            else -> CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD
                        },
                        eventCase = EventCase.ATTEMPT
                    )
                }
                is Resource.Success -> loggingPostAction(
                    context = this,
                    model = viewModel.postPressed,
                    eventName = when (viewModel.postPressed?.activity?.user?.role) {
                        UserType.BRAND.name -> CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD
                        else -> CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD
                    },
                    eventCase = EventCase.SUCCESS
                )
                else -> {
                    response.message?.let {
                        shortToast(it)
                        viewModel.updateFeedList(it).apply {
                            isUnFollowed?.value = true
                        }
                    }
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = when (viewModel.postPressed?.activity?.user?.role) {
                            UserType.BRAND.name -> CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD
                            else -> CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD
                        },
                        eventCase = EventCase.FAILURE
                    )
                }
            }
        }

        viewModel.unFollowPostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = when (viewModel.postPressed?.activity?.user?.role) {
                            UserType.BRAND.name -> CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD
                            else -> CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD
                        },
                        eventCase = EventCase.ATTEMPT
                    )
                }
                is Resource.Success ->
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = when (viewModel.postPressed?.activity?.user?.role) {
                            UserType.BRAND.name -> CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD
                            else -> CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD
                        },
                        eventCase = EventCase.SUCCESS
                    )
                else -> {
                    response.message?.let {
                        shortToast(it)
                        viewModel.updateFeedList(it).apply {
                            isUnFollowed?.value = false
                        }
                    }
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = when (viewModel.postPressed?.activity?.user?.role) {
                            UserType.BRAND.name -> CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD
                            else -> CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD
                        },
                        eventCase = EventCase.FAILURE
                    )
                }
            }
        }

        viewModel.deletePostLiveData.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = CustomEvent.USER_CONFIRM_DELETE_POST,
                        eventCase = EventCase.ATTEMPT
                    )
                }
                is Resource.Success -> {
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = CustomEvent.USER_CONFIRM_DELETE_POST,
                        eventCase = EventCase.SUCCESS
                    )
                    shortToast(getString(R.string.delete_title))
                    //in case of details page
                    if (currentRoute?.contains(Screen.Details.route) == true)
                        onBackPressed()
                }
                else -> {
                    response.message?.let {
                        shortToast(it)
                        viewModel.updateFeedList(it).apply {
                            isUserDeleting?.value = false
                        }
                    }
                    loggingPostAction(
                        context = this,
                        model = viewModel.postPressed,
                        eventName = CustomEvent.USER_CONFIRM_DELETE_POST,
                        eventCase = EventCase.FAILURE
                    )
                }
            }
        }

    }

    private fun setContent() {
        setContent {
            ProvideWindowInsets {

                val coroutineScope = rememberCoroutineScope()
                val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
                val keyboardController = LocalSoftwareKeyboardController.current
                val isBottomSheetExpanded = viewModel?.isBottomSheetExpanded?.observeAsState()

                navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                currentRoute = navBackStackEntry?.destination?.route
                index = navController.currentBackStackEntry?.arguments?.getInt("index") ?: 0

                val contentType =
                    navController.currentBackStackEntry?.arguments?.getInt("type") ?: 0

                var topBarTitle = ""

                when (contentType) {
                    SHARED_TYPE -> {
                        topBarTitle =
                            "${getString(R.string.foodies)} ${getString(R.string.who_shared)} ${foodiesList[index].followedUserName()}'s ${
                                getString(R.string.post)
                            }"
                        logMultipleEvents(
                            CustomEvent.USER_ESCAPE_FOODIES_LIST_GROUP_SHARING_ACTIVITY,
                            foodiesList[index - 1]
                        )
                    }
                    ORDERED_FROM_TYPE -> {
                        topBarTitle =
                            "${getString(R.string.foodies)} ${getString(R.string.who_ordered)} ${foodiesList[index].followedUserName()}'s ${
                                getString(R.string.post)
                            }"
                        logMultipleEvents(
                            CustomEvent.USER_ESCAPE_FOODIES_LIST_GROUP_ORDERING_ACTIVITY,
                            foodiesList[index - 1]
                        )//
                    }
                    COMMENTED_ON_TYPE -> {
                        topBarTitle =
                            "${getString(R.string.foodies)} ${getString(R.string.who_commented)} ${foodiesList[index].followedUserName()}'s ${
                                getString(R.string.post)
                            }"
                        logMultipleEvents(
                            CustomEvent.USER_ESCAPE_FOODIES_LIST_GROUP_COMMENTING_ACTIVITY,
                            foodiesList[index - 1]
                        )//
                    }
                    POSTED_ABOUT_TYPE -> {
                        topBarTitle = stringResource(id = R.string.foodies_posted_about)
                        logMultipleEvents(
                            CustomEvent.USER_ESCAPE_FOODIES_LIST_GROUP_POSTING_ACTIVITY,
                            foodiesList[index - 1]
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {

                    Scaffold(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .navigationBarsWithImePadding(),
                        topBar = {
                            when {
                                currentRoute?.contains(Screen.Pager.route) == true ||
                                        currentRoute?.contains(Screen.CreatePost.route) == true ->
                                    Log.e("", "")//
                                currentRoute?.contains(Screen.Details.route) == true -> DetailsTopBar(
                                    "${viewModel.postPressed?.activity?.user?.firstName} ${
                                        getString(
                                            R.string.post
                                        )
                                    }"
                                ) { onBackPressed() }
                                currentRoute?.contains(Screen.Report.route) == true -> ReportTopBar(
                                    stringResource(id = R.string.whats_wrong),
                                    isReport = true,
                                    index = index
                                ) { onBackPressed() }
                                currentRoute?.contains(Screen.FollowedFoodies.route) == true -> ReportTopBar(
                                    stringResource(id = R.string.follow_foodies)
                                ) {
                                    navController.navigateUp()
                                    logMultipleEvents(//LATER
                                        if (foodiesList[index - 1].isRestaurantActivity) CustomEvent.USER_ESCAPE_FOODIES_LIST_GROUP_FOLLOWING_RESTAURANT
                                        else CustomEvent.USER_ESCAPE_CLICK_FOODIES_LIST_GROUP_FOLLOWING_FOODIE,
                                        foodiesList[index - 1]
                                    )
                                }
                                currentRoute?.contains(Screen.ALLFoodies.route) == true -> ReportTopBar(
                                    topBarTitle
                                ) {
                                    navController.navigateUp()//
                                    logMultipleEvents(
                                        CustomEvent.USER_ESCAPE_SEE_ALL_FOLLOWED_FOODIES_LIST,
                                        foodiesList[index]
                                    )
                                }
                                currentRoute?.contains(Screen.ALLRestaurants.route) == true -> ReportTopBar(
                                    stringResource(id = R.string.follow_restaurants)
                                ) {
                                    navController.navigateUp()
                                    logMultipleEvents(//USER_ESCAPE_FOODIES_LIST_GROUP_FOLLOWING_RESTAURANT
                                        CustomEvent.USER_ESCAPE_SEE_ALL_FOLLOWED_RESTAURANTS_LIST,
                                        foodiesList[index - 1]
                                    )
                                }
                                currentRoute?.contains(Screen.ImagesEdit.route) == true -> CreatePostTopBar(
                                    buttonId = R.string.done,
                                    screenId = R.string.edit,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(height = 56.dp),
                                    isEnabled = isDoneEnabled.value,
                                    onBackClick = {
                                        onBackPressed()
                                    },
                                    onPostClick = {
                                        onBackPressed()
                                    })
                                currentRoute?.contains(Screen.TagObject.route) == true ->
                                    ReportTopBar(title = stringResource(id = R.string.tag_post)) {
                                        onBackPressed()
                                    }
                                currentRoute?.contains(Screen.Gallery.route) == true ->
                                    CreatePostTopBar(
                                        buttonId = R.string.add,
                                        screenId = R.string.restaurant_gallery,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(height = 56.dp),
                                        isEnabled = viewModel.galleryImageList.isNotEmpty(),
                                        onBackClick = {
                                            logEvent(
                                                event = CustomEvent.USER_CLICK_ADD_FROM_RESTAURANT_GALLERY,
                                                bundle = bundleOf(
                                                    CustomEventParams.OBJECT_TYPE.name to viewModel.getObjectType(),
                                                    CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel.isTagged.value) 1 else 0),
                                                    CustomEventParams.POST_WITH_TEXT.name to (if (viewModel.postText.value.isNotEmpty()) 1 else 0),
                                                    CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel.imagesList.isNotEmpty()) 1 else 0),
                                                ),
                                                eventCase = EventCase.SUCCESS
                                            )
                                            onBackPressed()
                                        },
                                        onPostClick = {
                                            logEvent(
                                                event = CustomEvent.USER_ESCAPE_RESTAURANT_GALLERY,
                                                bundle = bundleOf(
                                                    CustomEventParams.OBJECT_TYPE.name to viewModel.getObjectType(),
                                                    CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel.isTagged.value) 1 else 0),
                                                    CustomEventParams.POST_WITH_TEXT.name to (if (viewModel.postText.value.isNotEmpty()) 1 else 0),
                                                    CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel.imagesList.isNotEmpty()) 1 else 0),
                                                ),
                                                eventCase = EventCase.SUCCESS
                                            )
                                            mergeImages()
                                            navController.navigateUp()
                                        })
                                else -> TopBar()
                            }
                        },
                        bottomBar = {
                            when {
                                currentRoute?.contains(Screen.Details.route) == true ||
                                        currentRoute?.contains(Screen.Pager.route) == true ||
                                        currentRoute?.contains(Screen.Report.route) == true ||
                                        currentRoute?.contains(Screen.Profile.route) == true ||
                                        currentRoute?.contains(Screen.FollowedFoodies.route) == true ||
                                        currentRoute?.contains(Screen.ALLFoodies.route) == true ||
                                        currentRoute?.contains(Screen.ALLRestaurants.route) == true ||
                                        currentRoute?.contains(Screen.CreatePost.route) == true ||
                                        currentRoute?.contains(Screen.ImagesEdit.route) == true ||
                                        currentRoute?.contains(Screen.TagObject.route) == true
                                -> Log.e("", "")//show nothing
                                else -> BottomNavigationBar(navController)
                            }
                        }
                    ) { innerPadding ->
                        HomeNavHose(
                            navController = navController,
                            innerPadding = innerPadding,
                            viewModel = viewModel,
                            isDialogShowing = isDialogShowing,
                            isMentionText = isMentionText,
                            postText = viewModel.postText,
                            onPostClick = { postAnyway ->
                                sharedPreferencesManager.isFirstTime = false
                                if (!sharedPreferencesManager.isRulesConfirmed) {
                                    viewModel.isPosting.value = true
                                    viewModel.isBottomSheetExpanded.value = true
                                    return@HomeNavHose
                                }
                                if (viewModel.isTagged.value && viewModel.imagesList.isEmpty() && !postAnyway) {
                                    viewModel.isInetractionDialogShown.value = true
                                    return@HomeNavHose
                                }
                                logEvent(
                                    event = CustomEvent.USER_CLICK_POST,
                                    EventCase.ATTEMPT,
                                    bundle = bundleOf(
                                        CustomEventParams.OBJECT_TYPE.name to viewModel.getObjectType(),
                                        CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel.isTagged.value) 1 else 0),
                                        CustomEventParams.POST_WITH_TEXT.name to (if (viewModel.postText.value.isNotEmpty()) 1 else 0),
                                        CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel.imagesList.isNotEmpty()) 1 else 0)
                                    )
                                )
                                navController.navigateUp()
                                viewModel.createPost()
                            },
                            onCloseClick = {
                                viewModel.imagesList.remove(it)
                                viewModel.galleryImageList.remove(it)

                                isDoneEnabled.value =
                                    viewModel.imagesList.isNotEmpty() || viewModel.galleryImageList.isNotEmpty()
                            },
                            onAddImageClick = { isGallery ->
                                if (viewModel.imagesList.size >= 10) {
                                    shortToast(getString(R.string.max_images_recahes))
                                    return@HomeNavHose
                                }
                                when {
                                    !isGallery -> navController.navigate(Screen.Gallery.route)
                                    else -> GligarPicker().requestCode(PICKER_REQUEST_CODE)
                                        .withActivity(this@MainActivity)
                                        .apply {
                                            disableCamera(false)
                                            limit(10)
                                            setCurrentCount(viewModel.imagesListSize.value)
                                        }.show()
                                }
                            },
                            onTaggObjectClick = {
                                viewModel.clearTaggedList()
                                navController.navigate(Screen.TagObject.route)
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier
                        .background(lightBlack.copy(alpha = 0.3f))
                        .fillMaxSize()
                        .clickable(
                            enabled = true,
                            onClickLabel = ImageActions.CLOSE.clickLabel,
                            onClick = {
                                viewModel.isBottomSheetExpanded.postValue(false)
                                viewModel.sendCreatePostLog(CustomEvent.USER_CLICK_OUTSIDE_OVERLAY_ESCAPING_COMMUNITY_RULES)
                            }),
                    visible = isBottomSheetExpanded.value == true
                ) {
                    BottomSheetLayout(
                        modifier = Modifier.padding(top = 80.dp),
                        shape = RoundedCornerShape(
                            topEnd = 24.dp,
                            topStart = 24.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        ),
                        bottomState = bottomState
                    ) {
                        BottomSheetContent(viewModel.isPosting.value) { agreed ->
                            if (agreed) {
                                viewModel.createPost()
                                navController.navigateUp()
                            }

                            viewModel.sendCreatePostLog(
                                when {
                                    agreed -> CustomEvent.USER_CLICK_AGREE_COMMUNITY_RULES
                                    else -> CustomEvent.USER_CLICK_X_ESCAPING_COMMUNITY_RULES
                                }
                            )
                            viewModel.isBottomSheetExpanded.value = false
                        }
                    }
                }

                LaunchedEffect(viewModel.isBottomSheetExpanded.value) {
                    coroutineScope.launch {
                        keyboardController?.hide()
                        bottomState.animateTo(
                            when (viewModel.isBottomSheetExpanded.value) {
                                true -> ModalBottomSheetValue.Expanded
                                else -> ModalBottomSheetValue.Hidden
                            }
                        )
                    }
                }
            }
        }
    }

    private fun mergeImages() {
        viewModel.removeBrandGalleryImages()
        viewModel.imagesList.addAll(viewModel.galleryImageList)
        viewModel.galleryImageList.clear()
    }

    override fun onBackPressed() {
        when (currentRoute?.contains(Screen.Details.route) == true
                || currentRoute?.contains(Screen.Pager.route) == true
                || currentRoute?.contains(Screen.Report.route) == true
                || currentRoute?.contains(Screen.Profile.route) == true
                || currentRoute?.contains(Screen.FollowedFoodies.route) == true
                || currentRoute?.contains(Screen.ALLFoodies.route) == true
                || currentRoute?.contains(Screen.ALLRestaurants.route) == true
                || currentRoute?.contains(Screen.CreatePost.route) == true
                || currentRoute?.contains(Screen.ImagesEdit.route) == true
                || currentRoute?.contains(Screen.TagObject.route) == true
                || currentRoute?.contains(Screen.Gallery.route) == true) {
            true -> {
                when {
                    currentRoute?.contains(Screen.CreatePost.route) == true -> {
                        viewModel.usersMap.clear()
                        viewModel.resetCreatePostData()
                        navController.navigateUp()
                    }
                    currentRoute?.contains(Screen.Details.route) == true -> {
                        loggingPostAction(
                            context = this,
                            model = viewModel.postDetails,
                            eventName = CustomEvent.USER_ESCAPE_POST_DETAILS_PAGE,
                            eventCase = EventCase.SUCCESS
                        )

                        viewModel.feedListSnapShot.single { s -> s.activity._id == viewModel.postDetails?.activity?._id }
                            .apply {
                                viewModel.postDetails?.let {
                                    it.activity.likesCount = it.activity.likeCountMutable?.value
                                    it.activity.isLiked = it.activity.isLikedMutable?.value

                                    this.activity = it.activity
                                }
                            }

                        viewModel.clear()
                        navController.navigateUp()
                    }
                    currentRoute?.contains(Screen.Report.route) == true -> {
                        loggingPostAction(
                            context = this,
                            model = viewModel.postPressed,
                            eventName = CustomEvent.USER_EXIT_REPORT_POST_SCREEN,
                            eventCase = EventCase.SUCCESS
                        )
                        navController.navigateUp()
                    }
                    currentRoute?.contains(Screen.ImagesEdit.route) == true -> navController.navigateUp()
                    currentRoute?.contains(Screen.TagObject.route) == true -> navController.navigateUp()
                    currentRoute?.contains(Screen.Gallery.route) == true -> {
                        when {
                            !viewModel.galleryImageList.isNullOrEmpty() -> {
                                isDialogShowing.value = true
                            }
                            else -> navController.navigateUp()
                        }
                    }

                    else -> {
                        sharedPreferencesManager.isFirstTime = false
                        when {
                            viewModel.postText.value.isNotEmpty() || !viewModel.imagesList.isNullOrEmpty() -> {
                                logEvent(
                                    event = CustomEvent.USER_DISCARD_CREATE_POST,
                                    EventCase.SUCCESS
                                )
                                isDialogShowing.value = true
                            }
                            else -> navController.navigateUp()
                        }
                    }
                }
            }
            else -> finishAfterTransition()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            PICKER_REQUEST_CODE -> {
                logEvent(
                    event = CustomEvent.USER_CLICK_CONFIRM_SELECTED_IMAGES_CREATE_POST,
                    EventCase.SUCCESS,
                    bundle = bundleOf(
                        CustomEventParams.OBJECT_TYPE.name to viewModel?.getObjectType(),
                        CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel?.isTagged?.value) 1 else 0),
                        CustomEventParams.POST_WITH_TEXT.name to (if (viewModel.postText?.value?.isNotEmpty()) 1 else 0),
                        CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel?.imagesList?.isNotEmpty()) 1 else 0)
                    )
                )
                val imagesList =
                    data?.extras?.getStringArray(GligarPicker.IMAGES_RESULT)// return list of selected images paths.
                imagesList?.let {
                    viewModel.imagesList.addAll(imagesList.reversed())
                    isDoneEnabled.value = currentRoute?.contains(Screen.ImagesEdit.route) ?: false
                }
            }
        }
    }
}