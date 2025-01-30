package com.wemeal.presentation.onboarding.components.brand

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.onboarding.suggested_brands.Result
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.onboarding.components.follow.BrandFollowingBtn
import com.wemeal.presentation.shared.CoilImage
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalCoilApi
@SuppressLint("UnrememberedMutableState")
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewBrandItem() {
    BrandItem(
        brandModel = null, followCLick = {}
    )
}

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun BrandItem(
    brandModel: Result?,
    isShimmerEffect: Boolean = false,
    modifier: Modifier = Modifier,
    followCLick: (Boolean) -> Unit,
) {
    val imagePainter = rememberImagePainter(
        data = brandModel?.brandLogoURL ?: "https://www.example.com/image.jpg",
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)
            transformations(CircleCropTransformation())
        })
    val imageState = imagePainter.state

    val coverPainter = rememberImagePainter(
        data = brandModel?.coverPictureUrl ?: "https://www.example.com/image.jpg",
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    )
    val coverState = coverPainter.state

    Row(
        modifier = modifier
            .wrapContentSize()
    ) {
        Card(
            elevation = 2.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CoilImage(
                        painter = coverPainter,
                        placeholder = R.drawable.ic_cover_placeholder,
                        contentDescription = "COVER",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp)
                            .placeholder(
                                visible = isShimmerEffect || coverState is ImagePainter.State.Loading,
                                color = lightSky100,
                                // optional, defaults to RectangleShape
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = Color.White,
                                ),
                            )
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                            .placeholder(
                                visible = isShimmerEffect,
                                color = lightSky,
                                // optional, defaults to RectangleShape
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = Color.White,
                                ),
                            ),
                        text = brandModel?.name?.en ?: "",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        color = darkGrey,
                        letterSpacing = TextUnit(-0.49f, TextUnitType.Sp),
                        fontSize = 14.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(montserratSemiBold)
                        )
                    )
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 12.dp)
                    ) {
                        Text(
                            text = brandModel?.getFollowingsCount() ?: "",
                            maxLines = 1,
                            color = purple300,
                            letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                            fontSize = 12.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(montserratSemiBold)
                            ),
                            modifier = Modifier.placeholder(
                                visible = isShimmerEffect,
                                color = lightSky,
                                // optional, defaults to RectangleShape
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = Color.White,
                                ),
                            )
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 3.dp)
                                .align(CenterVertically)
                                .placeholder(
                                    visible = isShimmerEffect,
                                    color = lightSky,
                                    // optional, defaults to RectangleShape
                                    shape = RoundedCornerShape(4.dp),
                                    highlight = PlaceholderHighlight.fade(
                                        highlightColor = Color.White,
                                    ),
                                ),
                            text = stringResource(id = R.string.followers),
                            maxLines = 1,
                            color = black200,
                            letterSpacing = TextUnit(0.25f, TextUnitType.Sp),
                            lineHeight = TextUnit(16f, TextUnitType.Sp),
                            fontSize = 10.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(montserratRegular)
                            )
                        )
                    }
                    BrandFollowingBtn(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 11.dp)
                            .placeholder(
                                visible = isShimmerEffect,
                                color = lightSky,
                                // optional, defaults to RectangleShape
                                shape = RoundedCornerShape(20.dp),
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = Color.White,
                                )
                            ),
                        brandModel = brandModel,
                        checkCount = followCLick
                    )
                }
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 60.dp)
                        .size(64.dp)
                        .placeholder(
                            visible = isShimmerEffect || imageState is ImagePainter.State.Loading,
                            color = lightSky,
                            // optional, defaults to RectangleShape
                            shape = CircleShape,
                            highlight = PlaceholderHighlight.fade(
                                highlightColor = Color.White,
                            ),
                        ),
                    elevation = 10.dp,
                    shape = CircleShape
                ) {
                    CoilImage(
                        painter = imagePainter,
                        placeholder = R.drawable.ic_profile_placeholder,
                        contentDescription = "avatar",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 4.dp)
                    )
                }
            }
        }
    }

}