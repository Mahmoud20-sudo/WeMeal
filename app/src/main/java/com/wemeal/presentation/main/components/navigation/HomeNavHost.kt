package com.wemeal.presentation.main.components.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.wemeal.data.model.Img
import com.wemeal.data.model.Screen
import com.wemeal.presentation.main.screen.*
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.onboarding.screen.OnBoardingTwoScreen
import com.wemeal.presentation.util.*

@ExperimentalAnimationApi
@SuppressLint("UnrememberedMutableState")
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview
@Composable
fun PreviewHomeNavHose() {
    HomeNavHose(
        navController = rememberNavController(),
        innerPadding = PaddingValues(20.dp),
        viewModel = null,
        onPostClick = {},
        onAddImageClick = {},
        onTaggObjectClick = {},
        onCloseClick = {},
        isDialogShowing = mutableStateOf(false),
        postText = mutableStateOf(""),
        isMentionText = mutableStateOf(false)
    )
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun HomeNavHose(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: MainViewModel?,
    onPostClick: (postAnyway: Boolean) -> Unit,
    onCloseClick: (field: String) -> Unit,
    onAddImageClick: (isGallery: Boolean) -> Unit,
    onTaggObjectClick: () -> Unit,
    isDialogShowing: MutableState<Boolean>,
    postText: MutableState<String>,
    isMentionText: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            FeedScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Restaurants.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black
                    )
            )
        }
        composable(Screen.Offers.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Blue
                    )
            )
        }
        composable(Screen.Foodies.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Yellow
                    )
            )
        }
        composable(Screen.MyAccount.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Red
                    )
            )
        }
        composable(Screen.Details.route) {
            DetailsScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Pager.route) { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index") ?: ""
            ImagePagerScreen(
                navController = navController,
                index = index.toInt(),
                viewModel = viewModel
            )
        }
        composable(
            "${Screen.Report.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ReportScreen(navController = navController, id = id, viewModel = viewModel)
        }
        composable("${Screen.Profile.route}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ProfileScreen(navController = navController, id)
        }

        composable(
            "${Screen.FollowedFoodies.route}/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) {
            OnBoardingTwoScreen(isFromFeed = true, viewModel = null)
        }

        composable(
            "${Screen.ALLFoodies.route}/{index}/{type}",
            arguments = listOf(navArgument("index") { type = NavType.IntType },
                navArgument("type") { type = NavType.IntType })
        ) {
            AllFoodiesScreen()
        }
        composable(
            "${Screen.ALLRestaurants.route}/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) {
            OnBoardingTwoScreen(isFromFeed = true, viewModel = null)
        }
        composable(
            Screen.CreatePost.route
        ) {
            CreatePostScreen(
                navController = navController,
                viewModel = viewModel,
                isDialogShowing = isDialogShowing,
                isMentionText = isMentionText,
                postText = postText,
                onPostClick = onPostClick,
                onAddImageClick = onAddImageClick,
                onTaggObjectClick = onTaggObjectClick,
                onImageClick = {
                    navController.navigate(Screen.ImagesEdit.route)
                }
            )
        }
        composable(Screen.ImagesEdit.route) {
            ImagesEditScreen(
                viewModel = viewModel,
                onAddImageClick = onAddImageClick,
                onCloseClick = onCloseClick
            )
        }
        composable(Screen.TagObject.route) {
            TaggingObjectScreen(viewModel = viewModel) {
                navController.navigateUp()
            }
        }
        composable(Screen.Gallery.route) {
            when {
                viewModel?.taggedBrand?.value != null ->
                    RestaurantGalleryScreen(
                        viewModel = viewModel,
                        isDialogShowing = isDialogShowing
                    )
                viewModel?.taggedMeal?.value != null ->
                    MealGalleryScreen(viewModel = viewModel, isDialogShowing = isDialogShowing)
                viewModel?.taggedOffer?.value != null ->
                    OfferGalleryScreen(viewModel = viewModel, isDialogShowing = isDialogShowing)
                else -> OrderGalleryScreen(viewModel = viewModel, isDialogShowing = isDialogShowing)
            }
        }
    }
}