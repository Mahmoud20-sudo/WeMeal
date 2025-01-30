package com.wemeal.presentation.onboarding.screen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.intro.black
import com.wemeal.presentation.intro.components.ImageOnboarding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

private lateinit var job : Job

    @Preview
@Composable
fun PreviewOnBoardingLoadingScreen() {
    OnBoardingLoadingScreen { }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun OnBoardingLoadingScreen(onLoadingFinished: () -> Unit) {
    var progress by remember { mutableStateOf(0.0f) }
    val coroutineScope = rememberCoroutineScope()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(125.8.dp))
        ImageOnboarding(R.mipmap.ic_loading)
        Spacer(Modifier.height(102.dp))
        Text(
            text = stringResource(id = R.string.loading_message),
            maxLines = 1,
            color = black,
            letterSpacing = TextUnit(0.0f, TextUnitType.Sp),
            fontSize = 16.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(23.4.dp))
        LinearProgressIndicator(progress = animatedProgress)

        //temp using
        job = coroutineScope.launch {
            delay(1500)
            Log.e("AAA", progress.toString())
            if (progress < 1.0f)
                progress += 0.05f
            if (progress > 1.0f){
                onLoadingFinished()
//                job.cancelAndJoin() // cancels the job and waits for its completion
            }
        }
    }
}