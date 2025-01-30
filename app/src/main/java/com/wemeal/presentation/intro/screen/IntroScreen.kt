package com.wemeal.presentation.intro.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wemeal.R
import com.wemeal.presentation.intro.components.ButtonRounded
import com.wemeal.presentation.intro.components.ImageOnboarding
import com.wemeal.presentation.intro.components.IntroLoadingView
import com.wemeal.presentation.intro.components.Title
import com.wemeal.presentation.intro.facebookColor
import com.wemeal.presentation.intro.googleColor
import com.wemeal.presentation.shared.LoadingView
import com.wemeal.presentation.util.introTransitionDurationMillis
import com.wemeal.presentation.util.montserratSemiBold
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalUnitApi
@Preview
@Composable
fun PreviewIntroScreen() {
    IntroScreen(false, { }, { }, {})
}

@ExperimentalUnitApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroScreen(
    isLoading: Boolean,
    onFacebookBtnClick: () -> Unit,
    onGoogleBtnClick: () -> Unit,
    onSwipe: (page: Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = 3)

    val anim = rememberLottieAnimatable()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_login_loading))
    LaunchedEffect(composition) {
        anim.animate(
            composition,
            iterations = LottieConstants.IterateForever,
        )
    }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = com.wemeal.R.mipmap.app_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            val title = when (page) {
                0 -> "The Social Network for Food & Foodies"
                1 -> "Order your favourite food"
                else -> "Share your food experiences with friends"
            }

            val introImg = when (page) {
                0 -> com.wemeal.R.mipmap.intro_one
                1 -> com.wemeal.R.mipmap.intro_two
                else -> com.wemeal.R.mipmap.intro_three
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Title(
                    text = title,
                    fontFamily = FontFamily(montserratSemiBold),
                    modifier = Modifier.padding(start = 25.dp, end = 25.dp)
                )
                Spacer(Modifier.height(22.dp))
                ImageOnboarding(introImg)
                ButtonRounded(
                    onClick = onFacebookBtnClick,
                    text = "Continue with Facebook",
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, bottom = 24.dp, top = 40.dp)
                        .fillMaxWidth()
                        .height(40.dp),

                    com.wemeal.R.drawable.ic_facbook_icon,
                    facebookColor
                )
                ButtonRounded(
                    onClick = onGoogleBtnClick,
                    text = "Sign in with Google",
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, bottom = 36.dp)
                        .fillMaxWidth()
                        .height(40.dp),
                    com.wemeal.R.drawable.ic_google_icon,
                    googleColor
                )
            }
        }
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                // Selected page has changed...
                onSwipe(page)
                //AUTO-SCROLL every 7 secs
                launch {
                    while (pagerState.currentPage < pagerState.pageCount - 1) {
                        delay(introTransitionDurationMillis)
                        with(pagerState) {
                            val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                            animateScrollToPage(
                                page = target,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                }
            }
        }
        IntroLoadingView(anim = anim, isLoading = isLoading)
    }
}