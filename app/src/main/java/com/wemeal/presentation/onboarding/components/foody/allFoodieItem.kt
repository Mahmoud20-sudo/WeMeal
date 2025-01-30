package com.wemeal.presentation.onboarding.components.foody

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.github.kazemihabib.shimmer.ShimmerEffect
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.onboarding.suggested_foodies.Result
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.onboarding.components.follow.FoodieFollowingBtn
import com.wemeal.presentation.shared.CoilImage
import com.wemeal.presentation.shared.TextWithDrawable
import com.wemeal.presentation.util.ShimmerColorShades
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@SuppressLint("UnrememberedMutableState")
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewFoodyItem() {
//    FoodyItem(foodieModel =, followCLick = {})
}

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun FoodyItem(
    modifier: Modifier = Modifier,
    isShimmerEffect: Boolean = false,
    foodieModel: Result?,
    followCLick: (Boolean) -> Unit = {}
) {

    val imagePainter = rememberImagePainter(data = foodieModel?.profilePictureUrl ?: "",
        builder = {
            transformations(CircleCropTransformation())
            memoryCachePolicy(CachePolicy.ENABLED)
        })
    val imageState = imagePainter.state

    val coverPainter = rememberImagePainter(
        data = foodieModel?.coverPictureUrl ?: "",
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)

        })
    val coverState = coverPainter.state

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
                .wrapContentSize()
                .padding(bottom = 20.dp),
        ) {
            val (image, coverImg, userNameText, postsCountText, postsText, followersCountText, followersText, followButton) = createRefs()
            CoilImage(
                painter = coverPainter,
                placeholder = R.drawable.ic_cover_placeholder,
                contentDescription = "COVER",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .constrainAs(coverImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .fillMaxWidth()
                    .height(56.dp)
                    .placeholder(
                        visible = isShimmerEffect || (coverState is ImagePainter.State.Loading),
                        color = lightSky100,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    )
            )
            Surface(
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 20.dp)
                    .size(64.dp)
                    .placeholder(
                        visible = isShimmerEffect || (imageState is ImagePainter.State.Loading),
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = CircleShape,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        )
                    ),
                shape = CircleShape,
                elevation = 10.dp
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
            TextWithDrawable(
                text = "${foodieModel?.firstName} ${foodieModel?.lastName}",
                texSize = 14,
                textColor = black300,
                resourceId = if (foodieModel?.isVerified == true) R.drawable.ic_verified else 0,
                drawableSize = 15,
                drawablePadding = 4,
                shape = CircleShape,
                linesNumber = 1,
                fontWeight = FontWeight.Normal,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .constrainAs(userNameText) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(top = 13.dp, start = 10.dp, end = 10.dp)
                    .placeholder(
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
                    .constrainAs(followersCountText) {
                        top.linkTo(userNameText.bottom)
                        start.linkTo(followersText.start)
                        end.linkTo(followersText.end)
                    }
                    .padding(top = 20.dp, end = 18.dp)
                    .placeholder(
                        visible = isShimmerEffect,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        )
                    ),
                text = foodieModel?.getFollowingsCount() ?: "",
                maxLines = 1,
                color = purple300,
                textAlign = TextAlign.Center,
                letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                fontSize = 12.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratSemiBold)
                )
            )
            Text(
                modifier = Modifier
                    .constrainAs(followersText) {
                        top.linkTo(followersCountText.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 3.dp, end = 20.dp)
                    .placeholder(
                        visible = isShimmerEffect,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        )
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
            Text(
                modifier = Modifier
                    .constrainAs(postsCountText) {
                        top.linkTo(userNameText.bottom)
                        start.linkTo(postsText.start)
                        end.linkTo(postsText.end)
                    }
                    .padding(top = 20.dp, start = 23.dp)
                    .placeholder(
                        visible = isShimmerEffect,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        )
                    ),
                text = foodieModel?.getPostsCount() ?: "",
                maxLines = 1,
                color = purple300,
                letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                fontSize = 12.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratSemiBold)
                )
            )
            Text(
                modifier = Modifier
                    .constrainAs(postsText) {
                        top.linkTo(postsCountText.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(top = 3.dp, start = 25.dp)
                    .placeholder(
                        visible = isShimmerEffect,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        )
                    ),
                text = stringResource(id = R.string.posts),
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
            FoodieFollowingBtn(
                modifier = Modifier
                    .constrainAs(followButton) {
                        top.linkTo(postsText.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 15.dp)
                    .placeholder(
                        visible = isShimmerEffect,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(20.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        )
                    ),
                foodiesModel = foodieModel,
                checkCount = followCLick
            )
        }
    }
}

//@ExperimentalCoilApi
//@OptIn(ExperimentalUnitApi::class)
//@Composable
//fun ShimmerItem(
//    brush: Brush
//) {
//    // Column composable containing spacer shaped like a rectangle,
//    // set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
//    // Composable which is the Animation you are gonna create.
////    Column(modifier = Modifier.padding(16.dp)) {
////        Spacer(
////            modifier = Modifier
////                .fillMaxWidth()
////                .size(250.dp)
////                .background(brush = brush)
////        )
////        Spacer(
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(30.dp)
////                .padding(vertical = 8.dp)
////                .background(brush = brush)
////        )
////    }
//    FoodyItem(
//        modifier = Modifier
//            .background(brush = brush)
//            .padding(start = 5.dp, end = 4.5.dp, bottom = 10.dp),
//        foodieModel = null
//    )
//}
//
//@ExperimentalCoilApi
//@Composable
//fun ShimmerAnimation(
//) {
//    /*
//     Create InfiniteTransition
//     which holds child animation like [Transition]
//     animations start running as soon as they enter
//     the composition and do not stop unless they are removed
//    */
//    val transition = rememberInfiniteTransition()
//    val translateAnim by transition.animateFloat(
//        /*
//         Specify animation positions,
//         initial Values 0F means it
//         starts from 0 position
//        */
//        initialValue = 0f,
//        targetValue = 1000f,
//        animationSpec = infiniteRepeatable(
//
//
//            // Tween Animates between values over specified [durationMillis]
//            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
//            RepeatMode.Reverse
//        )
//    )

/*
  Create a gradient using the list of colors
  Use Linear Gradient for animating in any direction according to requirement
  start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
  end = Animate the end position to give the shimmer effect using the transition created above
*/
//    val brush = Brush.linearGradient(
//        colors = ShimmerColorShades,
//        start = Offset(10f, 10f),
//        end = Offset(translateAnim, translateAnim)
//    )
//
//    ShimmerItem(brush = brush)
//}