package com.wemeal.presentation.onboarding.components.follow

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.data.model.onboarding.suggested_foodies.Result
import com.wemeal.presentation.intro.lightPurple
import com.wemeal.presentation.intro.purple300
import com.wemeal.presentation.intro.regularBlue
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratSemiBold

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FoodieFollowingBtn(
    modifier: Modifier,
    foodiesModel: Result? = null,
    isFollowed: MutableState<Boolean> = mutableStateOf(false),
    checkCount: (Boolean) -> Unit
) {
    isFollowed.value = foodiesModel?.isFollowing ?: false

    Row(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        regularBlue,
                        lightPurple
                    )
                ),
                shape = RoundedCornerShape(20.dp),
                alpha = if (isFollowed.value) 0.2f else 1.0f
            )
            .wrapContentWidth()
            .height(32.dp)
            .padding(top = 6.dp, start = 16.dp, end = 16.dp, bottom = 6.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Follow/Unfollow",
                onClick = {
                    foodiesModel?.isFollowing = !(foodiesModel?.isFollowing!!)
                    checkCount(foodiesModel.isFollowing!!)
                    foodiesModel.followersCount =
                        when {
                            foodiesModel.isFollowing!! -> foodiesModel.followersCount?.plus(1)
                            else -> foodiesModel.followersCount?.minus(1)
                        }
                }
            )
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 7.dp), visible = isFollowed.value
        ) {
            Image(
                modifier = Modifier
                    .size(12.dp),
                painter = painterResource(id = R.drawable.ic_tick),
                contentDescription = "COVER",
                contentScale = ContentScale.FillBounds
            )
        }
        Text(
            text = stringResource(id = if (isFollowed.value) R.string.following else R.string.follow),
            textAlign = TextAlign.Center,
            modifier = Modifier,
            maxLines = 1,
            color = if (isFollowed.value) purple300 else Color.White,
            fontSize = 14.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratMedium)
            )
        )
    }
}