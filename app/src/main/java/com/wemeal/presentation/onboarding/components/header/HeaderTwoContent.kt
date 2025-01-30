package com.wemeal.presentation.onboarding.components.header

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.onboarding.components.follow.FollowingView
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalUnitApi
@Preview
@Composable
fun PreviewHeaderTwoContent() {
    HeaderTwoContent(remember {
        mutableStateOf(0)
    })
}

@ExperimentalUnitApi
@Composable
fun HeaderTwoContent(count: MutableState<Int>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.follow_resturants_message, ""),
            color = Color.White,
            fontSize = 14.sp,
            letterSpacing = TextUnit(-0.49f, TextUnitType.Sp),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold)
            ), modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
        FollowingView(count, page = 2)
    }
}