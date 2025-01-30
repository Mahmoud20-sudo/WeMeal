package com.wemeal.presentation.onboarding

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.presentation.BaseActivity
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.closeApp
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.onboarding.screen.FollowFoodiesScreen
import com.wemeal.presentation.onboarding.screen.OnBoardingLoadingScreen
import com.wemeal.presentation.onboarding.screen.OnBoardingOneScreen
import com.wemeal.presentation.onboarding.screen.OnBoardingTwoScreen
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModelFactory
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.meta.When
import javax.inject.Inject

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class OnboardingActivity : BaseActivity() {

    @Inject
    lateinit var factory: OnBoardingViewModelFactory

    lateinit var viewModel: OnBoardingViewModel
    private lateinit var navController: NavHostController
    private val isLoading = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarIconsColor(
            color = R.color.boarding_status_bar_color,
            shouldChangeStatusBarTintToDark = false
        )
        initialization()
        initObservers()
        setContent()
    }

    @Composable
    fun StartScreen() {
        OnBoardingOneScreen(loadingDetails = isLoading, page = 1, viewModel = viewModel) {
            viewModel.confirmArea()
        }
    }

    @Composable
    fun SecondScreen(navController: NavController) {
        OnBoardingTwoScreen(viewModel = viewModel, page = 2) {
            navController.navigate(FOODIES_SCREEN)
        }
    }

    @Composable
    fun ThirdScreen(navController: NavController) {
        FollowFoodiesScreen(viewModel = viewModel, page = 3) {
            navController.navigate(FINISHING_SCREEN)
        }
    }

    @Composable
    fun FinishingScreen() {
        viewModel.updateUser()
        OnBoardingLoadingScreen {
            MainActivityArgs().launch(this)
            finishAfterTransition()
        }
    }

    override fun onBackPressed() {
        closeApp()
    }

    private fun setContent() {
        setContent {
            navController = rememberNavController()
            Scaffold {
                NavHost(navController, startDestination = MAP_SCREEN) {
                    composable(MAP_SCREEN) {
                        StartScreen()
                    }
                    composable(RESTAURANTS_SCREEN) {
                        SecondScreen(navController)
                    }
                    composable(FOODIES_SCREEN) {
                        ThirdScreen(navController)
                    }
                    composable(FINISHING_SCREEN) {
                        FinishingScreen()
                    }
                }
            }
        }
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this, factory)[OnBoardingViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.confirmAreaLiveDate.observe(this, { response ->
            when (response) {
                is Resource.Loading ->
                    isLoading.value = true
                is Resource.Success -> {
                    isLoading.value = false
                    navController.navigate(RESTAURANTS_SCREEN)
                }
                else -> {
                    isLoading.value = false
                    response.message?.let { shortToast(it) }
                }
            }
        })
        viewModel.subAreasLiveData.observe(this, { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    if (viewModel.subAreasPage.value == 1)
                        logEvent(
                            CustomEvent.USER_SELECT_AREA_FROM_LIST,
                            EventCase.SUCCESS
                        )
                }
                else -> {
                    if (viewModel.subAreasPage.value == 1)
                        logEvent(
                            CustomEvent.USER_SELECT_AREA_FROM_LIST,
                            EventCase.FAILURE
                        )
                }
            }
        })

        viewModel.areasLiveData.observe(this, { response ->
            when (response) {
                is Resource.Loading -> Log.i(LOADING, LOADING)
                is Resource.Success -> {
                    if (viewModel.areasPage.value == 1)
                        logEvent(
                            CustomEvent.USER_SELECT_CITY_FROM_LIST,
                            EventCase.SUCCESS
                        )
                }
                else -> {
                    if (viewModel.areasPage.value == 1)
                        logEvent(
                            CustomEvent.USER_SELECT_CITY_FROM_LIST,
                            EventCase.FAILURE
                        )
                }
            }
        })

    }
}