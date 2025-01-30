package com.wemeal.presentation.onboarding.components.follow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalUnitApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FollowingView(
    count: MutableState<Int>,
    page: Int
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(
                id = when (page) {
                    2 -> R.string.follow_three_resturants
                    else -> R.string.follow_three_foodies
                },
                ""
            ),
            color = Color.White.copy(0.7f),
            fontSize = 12.sp,
            letterSpacing = TextUnit(-0.42f, TextUnitType.Sp),
            style = TextStyle(
                fontWeight = FontWeight.Normal
            )
        )
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterEnd),
            visible = count.value > 0
        ) {
            Text(
                text = "${count.value} ${stringResource(id = R.string.followings, "")}",
                color = Color.White,
                fontSize = 12.sp,
                letterSpacing = TextUnit(-0.42f, TextUnitType.Sp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratRegular)
                )
            )
        }
    }
}