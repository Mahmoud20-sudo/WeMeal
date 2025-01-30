package com.wemeal.presentation.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.wemeal.R
import com.wemeal.data.model.AuthType
import com.wemeal.presentation.BaseActivity
import com.wemeal.presentation.intro.screen.IntroScreen
import com.wemeal.presentation.intro.viewModel.AuthViewModel
import com.wemeal.presentation.intro.viewModel.AuthViewModelFactory
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.wemeal.data.model.Resource
import com.wemeal.data.model.user.UserModel
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.util.*

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class IntroActivity : BaseActivity() {

    override val isFullscreen: Boolean
        get() = true

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var factory: AuthViewModelFactory
    lateinit var viewModel: AuthViewModel
    private val sharedPreferencesManager = SharedPreferencesManager.instance
    private val isLoading = MutableLiveData(false)
    lateinit var loginMethod: AuthType

//    private val launcher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->  //Intent
//            if (result.resultCode == Activity.RESULT_OK) {
//                // handle the response in result.data
//                viewModel.handleOnActivityResult(result.resultCode, result.data)
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSplash(savedInstanceState)
        initialization()
        checkInternetConnection()
    }

    override fun onStart() {
        super.onStart()
        navigateActivity(sharedPreferencesManager.user)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handleOnActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeApp()
    }

    @Composable
    fun StartScreen() {
        IntroScreen(
            isLoading = isLoading.value!!,
            onFacebookBtnClick = {
                doSocialAuth(AuthType.FACEBOOK)
            },
            onGoogleBtnClick = {
                doSocialAuth(AuthType.GOOGLE)
            }, onSwipe = { page ->
                logPaging(page = page)
            })
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        initObservers()
    }

    private fun initObservers() {
        observe(viewModel.uiState) { authModel ->
            when {
                authModel.showProgress -> isLoading.postValue(true)

                authModel.success && firebaseAuth.currentUser != null -> {
                    viewModel.loginRemote(
                        loginMethod.authValue,
                        authModel.accessToken
                    )
                }

                authModel.error != null && !authModel.error.consumed -> authModel.error.consume()
                    ?.let {
                        isLoading.postValue(false)
//                        showSimpleDialog(this, pair.second) {
//                            doSocialAuth(pair.first)
//                        }
                    }
            }
        }
        observe(viewModel.userLiveData) { response ->
            when (response) {
                is Resource.Success -> {
                    navigateActivity(response.data)
                    //LOG SUCCESS EVENTS
                    when (loginMethod) {
                        AuthType.GOOGLE -> logEvent(CustomEvent.USER_CONTINUE_WITH_GOOGLE, EventCase.SUCCESS)
                        AuthType.FACEBOOK -> logEvent(CustomEvent.USER_CONTINUE_WITH_FACEBOOK, EventCase.SUCCESS)
                    }
                }
                is Resource.Error -> {
                    isLoading.postValue(false)
                    response.message?.let {
                        shortToast(it)
                    }
                    val bundle = bundleOf("message" to response.message)
                    //LOG FAIL EVENTS
                    when (loginMethod) {
                        AuthType.GOOGLE -> logEvent(
                            CustomEvent.USER_CONTINUE_WITH_GOOGLE,
                            EventCase.FAILURE,
                            bundle
                        )
                        AuthType.FACEBOOK -> logEvent(
                            CustomEvent.USER_CONTINUE_WITH_FACEBOOK,
                            EventCase.FAILURE,
                            bundle
                        )
                    }
                }
                is Resource.Loading -> Log.i(LOADING, LOADING)
            }
        }
    }

    private fun logPaging(page: Int) {
        when (page) {
            0 -> logEvent(CustomEvent.USER_VIEW_INTRO_SCREEN_1, EventCase.SUCCESS)
            1 -> logEvent(CustomEvent.USER_VIEW_INTRO_SCREEN_2, EventCase.SUCCESS)
            2 -> logEvent(CustomEvent.USER_VIEW_INTRO_SCREEN_3, EventCase.SUCCESS)
        }
    }

    private fun setSplash(savedInstanceState: Bundle?) {
        val splashWasDisplayed = savedInstanceState != null
        if (!splashWasDisplayed) {
            logEvent(CustomEvent.USER_LAUNCH_APP, EventCase.SUCCESS)
            splashAnimator(splashScreen = installSplashScreen()) {
                setContent()
            }
        } else {
            setTheme(R.style.Theme_WeMeal)
            setContent()
        }
    }

    private fun setContent() {
        isLoading.observe(this, {
            setContent {
                StartScreen()
            }
        })
    }

    private fun navigateActivity(data: UserModel?) {
        if (data == null) return
        sharedPreferencesManager.user = data
        when (sharedPreferencesManager.isDoneOnBoarding ?: false) {
            true -> MainActivityArgs().launch(this)//OnBoardingActivityArgs
            false -> OnBoardingActivityArgs().launch(this)//OnBoardingActivityArgs
        }
        finish()
    }

    private fun doSocialAuth(authType: AuthType) {
        when (authType) {
            AuthType.GOOGLE -> viewModel.googleSignIn().also {
                googleSignInClient.signOut()
                loginMethod = AuthType.GOOGLE
                startActivityForResult(it, RC_GOOGLE_SIGN_IN_CODE)
            }
            AuthType.FACEBOOK -> {
                loginMethod = AuthType.FACEBOOK
                viewModel.facebookSignIn()
            }
        }
    }
}

//                authModel.showAllLinkProvider != null && !authModel.showAllLinkProvider.consumed -> authModel.showAllLinkProvider.consume()
//                    ?.let { pair ->
//                        showSingleChoiceDialog(
//                            this, pair.second, pair.first,
//                            negativeButtonClickListener = {
//                            }) {
//                            val authType = it.toAuthType()
//                            doSocialAuth(authType)
//                        }
//                    }