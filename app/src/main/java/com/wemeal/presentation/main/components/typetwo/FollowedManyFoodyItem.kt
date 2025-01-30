package com.wemeal.presentation.main.components.typetwo

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.*
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.ClickableImage
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.wemeal.presentation.util.*

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewFollowedManyFoodyItem() {
    FollowedManyFoodyItem(
        foodyModel = FoodyModel(
            id = 1, firstName = "Mahmoud",
            lastName = "Mohamed",
            img = R.drawable.ic_temp,
            cover = R.mipmap.ic_cockdoor_cover,
            followersCount = 8.2f,
            postsCount = 235,
            isVerified = true,
            text = "HELLO",
            isFollowed = mutableStateOf(false),
            isUnFollowed = mutableStateOf(false)
        )
    ) {}
}

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalAnimationApi
@Composable
fun FollowedManyFoodyItem(
    foodyModel: FoodyModel,
    onProfileClick: () -> Unit = { },
    onOtherFoodiesClick: (isSeeAll : Boolean) -> Unit
) {

    val context = LocalContext.current
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 12.dp
            ),
    ) {
        val (userImage, userNameText, followedUsersLazyRow, seeAllText) = createRefs()
        ClickableImage(
            drawableId = foodyModel.img,
            imageActions = ImageActions.VIEW,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(32.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .constrainAs(userImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop
        ) {
            onProfileClick()
        }
        SetText(
            foodyModel = foodyModel,
            texSize = 12.sp,
            color = black300,
            lines = 2,
            followersCount = foodyModel.otherFoodiesCount,
            modifier = Modifier
                .constrainAs(userNameText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .padding(top = 1.dp, start = 56.dp, end = 16.dp),
            onOtherFoodiesClick = onOtherFoodiesClick,
            onProfileClick = onProfileClick
        )
        LazyRow(
            modifier = Modifier
                .constrainAs(followedUsersLazyRow) {
                    top.linkTo(userImage.bottom)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            contentPadding = PaddingValues(top = 12.dp)
        ) {
//            if (foodyModel.isRestaurantActivity)
//                items(resturantsList.subList(0, foodyModel.otherFoodiesCount).size) { index ->
//                    RestaurantItem(
//                        restaurantModel = resturantsList[index],
//                        modifier = Modifier
////                            .setGestureModifier(
////                                context,
////                                CustomEvent.USER_SWIPE_FOLLOWED_RESTAURANTS_CAROUSEL, foodyModel
////                            )
//                            .width(178.dp)
//                            .padding(start = 5.dp, end = 4.5.dp, bottom = 10.dp)
//                    ) { isFollowed ->
//                        context.logMultiEvents(
//                            if (isFollowed) CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD else CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD,
//                            foodyModel = foodyModel
//                        )
//                    }
//                }
//            else
//                items(followedFoodiesList.subList(0, foodyModel.otherFoodiesCount).size) { index ->
////                    FoodyItem(
////                        foodyModel = followedFoodiesList[index],
////                        modifier = Modifier
//////                            .setGestureModifier(
//////                                context,
//////                                CustomEvent.USER_SWIPE_FOLLOWED_FOODIES_CAROUSEL,//
//////                                foodyModel
//////                            )
////                            .width(178.dp)
////                            .padding(start = 5.dp, end = 4.5.dp, bottom = 10.dp)
////                    ) { isFollowed ->
////                        context.logMultiEvents(
////                            if (isFollowed) CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD else CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD,
////                            foodyModel = foodyModel
////                        )
////                    }
//                }
        }
        com.wemeal.presentation.shared.ClickableText(
            modifier = Modifier
                .constrainAs(seeAllText) {
                    top.linkTo(followedUsersLazyRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 2.dp),
            text = stringResource(id = R.string.see_all),
            color = purple600,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = FontFamily(montserratSemiBold)
            )
        ) {
            onOtherFoodiesClick(true)
        }
    }
}

@Composable
private fun SetText(
    foodyModel: FoodyModel,
    texSize: TextUnit,
    color: Color,
    lines: Int,
    modifier: Modifier,
    onOtherFoodiesClick: (isSeeAll : Boolean) -> Unit,
    followersCount: Int,
    onProfileClick: () -> Unit
) {

    val userName = "${foodyModel.firstName} ${foodyModel.lastName}"
    val context = LocalContext.current
    val hasFollowedText = " ${context.getString(R.string.has_followed)} "
    val andText = " ${context.getString(R.string.and)} "

    val others = if (foodyModel.isRestaurantActivity) context.getString(R.string.other_restaurants)
    else context.getString(R.string.other_foodies)

    val otherFoodiesText = "$followersCount $others"
    val allText =
        "$userName $hasFollowedText ${foodyModel.followedUserName} $andText $otherFoodiesText"

    val annotatedString = buildAnnotatedString {

        addStringAnnotation(
            tag = userName,
            annotation = userName,
            start = 0,
            end = userName.length
        )
        withStyle(
            style = SpanStyle(
                color = color,
                fontSize = texSize,
                fontFamily = FontFamily(montserratBold)
            )
        ) {
            append(userName)
        }

        withStyle(
            style = SpanStyle(
                color = color,
                fontSize = texSize,
                fontFamily = FontFamily(montserratMedium)
            )
        ) {
            append(hasFollowedText)
        }

        addStringAnnotation(
            tag = "terms",
            annotation = foodyModel.followedUserName,
            start = allText.length - foodyModel.followedUserName.length - otherFoodiesText.length - andText.length,
            end = allText.length - foodyModel.followedUserName.length - otherFoodiesText.length - andText.length + foodyModel.followedUserName.length
        )
        withStyle(
            style = SpanStyle(
                color,
                fontSize = texSize,
                fontFamily = FontFamily(montserratBold)
            )
        ) {
            append(foodyModel.followedUserName)
        }

        withStyle(
            style = SpanStyle(
                color = color,
                fontSize = texSize,
                fontFamily = FontFamily(montserratMedium)
            )
        ) {
            append(andText)
        }

        addStringAnnotation(
            tag = "others",
            annotation = "others",
            start = allText.length - otherFoodiesText.length,
            end = allText.length - otherFoodiesText.length + otherFoodiesText.length
        )
        withStyle(
            style = SpanStyle(
                color,
                fontSize = texSize,
                fontFamily = FontFamily(montserratBold)
            )
        ) {
            append(otherFoodiesText)
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = modifier.height(30.dp),
        maxLines = lines,
        overflow = TextOverflow.Ellipsis,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = userName, start = offset, end = offset)
                .firstOrNull()?.let {
                    Log.d("policy URL", it.item)
                    onProfileClick()
                }

            annotatedString.getStringAnnotations(
                tag = "terms",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("terms URL", it.item)
                    onProfileClick()
                }

            annotatedString.getStringAnnotations(
                tag = "others",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("others URL", it.item)
                    onOtherFoodiesClick(false)
                }
        })
}


