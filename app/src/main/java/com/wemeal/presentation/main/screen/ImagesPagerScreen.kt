package com.wemeal.presentation.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.wemeal.R
import com.wemeal.presentation.intro.black400
import com.wemeal.presentation.intro.grey300
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.ZoomableImage
import com.wemeal.presentation.util.ImageActions
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Preview
@Composable
fun PreviewDetailsImagePagerScreen() {
    ImagePagerScreen(0, viewModel = null, rememberNavController())
}

@ExperimentalPagerApi
@Composable
fun ImagePagerScreen(index: Int, viewModel: MainViewModel?, navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = viewModel?.postPressed?.activity?.getImages()?.size ?: 0)

    LaunchedEffect(pagerState) {
        coroutineScope.launch {
            pagerState.scrollToPage(index)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(black400)
    ) {
        ClickableImage(
            drawableId = R.drawable.ic_clear,
            imageActions = ImageActions.CLOSE,
            modifier = Modifier
                .align(End)
                .padding(all = 16.dp)
                .size(20.dp)
                .padding(3.dp)
        ) {
            navController.navigateUp()
            //context.logMultipleEvents(CustomEvent.USER_CLICK_X_EXIT_SCREEN_FULL_MODE, foodiesList[index])
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            Card(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxWidth()
                    .wrapContentHeight(),
                backgroundColor = black400,
                elevation = 0.dp
            ) {
                ZoomableImage(
                    painter= rememberImagePainter(data = viewModel?.postPressed?.activity?.getImages()?.get(page)),
                    isRotation = false,
                    contentScale = ContentScale.Fit,
                    alignment = Center,
                    modifier = Modifier.fillMaxWidth()
                ) { isZoomIn ->
//                    context.logMultipleEvents(
//                        if (isZoomIn)
//                            CustomEvent.USER_ZOOM_IN_IMAGES_ON_POST
//                        else
//                            CustomEvent.USER_ZOOM_OUT_IMAGES_ON_POST, foodiesList[index]
                                            // ) //foodiesList[index] will be changed later
                }
            }
//            context.logMultipleEvents(
//                CustomEvent.USER_SWIPE_IMAGES_ON_POST,
//                foodiesList[index]
//
//            ) //foodiesList[index] will be changed later
        }

        HorizontalPagerIndicator(
            activeColor = Color.White,
            inactiveColor = grey300,
            indicatorHeight = 6.dp,
            indicatorWidth = 6.dp,
            indicatorShape = CircleShape,
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )

        // Later, scroll to page 2
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float =
    (1 - fraction) * start + fraction * stop

//                Image(
//                    painter = painterResource(
//                        id = imgs[page].drawable,
//                    ),
//                    contentDescription = "",
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier
//                        .graphicsLayer(
//                            scaleX = scale,
//                            scaleY = scale
//                        ).pointerInput(Unit) {
//                            detectTransformGestures { _, _, zoom, _ ->
//                                scale = when {
//                                    scale < 0.5f -> 0.5f
//                                    scale > 3f -> 3f
//                                    else -> scale * zoom
//                                }
//                            }
//                        }
//                )