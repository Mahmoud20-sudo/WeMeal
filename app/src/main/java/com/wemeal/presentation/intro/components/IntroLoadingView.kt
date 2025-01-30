package com.wemeal.presentation.intro.components

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.ImageLoader
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.decode.ImageDecoderDecoder
import com.airbnb.lottie.compose.*
import com.bumptech.glide.gifdecoder.GifDecoder
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.presentation.intro.darkGrey
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalUnitApi
@Preview
@Composable
private fun PreviewIntroLoadingView() {
    IntroLoadingView(true, anim = rememberLottieAnimatable())
}

@ExperimentalUnitApi
@Composable
fun IntroLoadingView(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    anim: LottieAnimatable
) {
    if (isLoading) {

        val context = LocalContext.current

        val imgLoader = ImageLoader.invoke(context).newBuilder()
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(context))
                } else {
                    add(coil.decode.GifDecoder())
                }
            }.build()


        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.mipmap.app_background),
                contentDescription = "Background",
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LottieAnimation(
                    anim.composition, anim.progress,
                    modifier = modifier
                        .background(color = Color.Transparent)
                        .padding(start = 35.dp, end = 25.dp)
                        .fillMaxWidth()
                        .height(288.dp)
                )
//                Image(
//                    painter = rememberImagePainter(
//                        data = R.drawable.ic_auth_loading,
//                        imageLoader = imgLoader
//                    ),
//                    contentDescription = ImageActions.VIEW.contentDescription,
//                    modifier = modifier
//                        .background(color = Color.Transparent)
//                        .padding(start = 35.dp, end = 25.dp)
//                        .fillMaxWidth()
//                        .height(288.dp)
//                )

                Text(
                    text = stringResource(id = R.string.auth_loading_message),
                    color = Color.White,
                    fontSize = 18.sp,
                    style = TextStyle(
                        fontFamily = FontFamily(montserratSemiBold),
                        letterSpacing = TextUnit(-0.63f, TextUnitType.Sp)
                    ),
                    modifier = modifier.padding(top = 44.dp)
                )
            }

        }
    }
}

