package com.wemeal.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.presentation.extensions.launch
import com.wemeal.presentation.main.MainActivity
import com.wemeal.presentation.onboarding.OnboardingActivity

interface ActivityArgs {

    fun intent(context: Context): Intent

    fun launch(context: Context, options: Bundle? = null) =
        context.launch(intent = intent(context), options = options)

    fun launch(activity: Activity, options: Bundle? = null, requestCode: Int = -1) =
        activity.launch(intent = intent(activity).addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
        ), requestCode = requestCode, options = options)
}

@ExperimentalComposeUiApi
@ExperimentalUnitApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalPagerApi
class OnBoardingActivityArgs : ActivityArgs {
    override fun intent(context: Context) = Intent(context, OnboardingActivity::class.java)
}

@ExperimentalComposeUiApi
@ExperimentalUnitApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
class MainActivityArgs : ActivityArgs {
    override fun intent(context: Context) = Intent(context, MainActivity::class.java)
}