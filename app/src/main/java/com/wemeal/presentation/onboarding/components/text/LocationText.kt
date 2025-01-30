package com.wemeal.presentation.onboarding.components.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.presentation.intro.darkBlack
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.util.montserratBold

@ExperimentalUnitApi
@Preview
@Composable
fun PreviewLocationText() {
    LocationText(true, null)
}

@ExperimentalUnitApi
@Composable
fun LocationText(
    isLoading: Boolean, viewModel: OnBoardingViewModel?
) {

    val subAreaName = viewModel?.locationModel?.value?.result?.get(0)?.subAreaOnlyName.let {
        if (it == null) "" else "${it.en}, "
    }
    val areaName = viewModel?.locationModel?.value?.result?.get(0)?.areaOnlyName.let {
        if (it == null) "" else "${it.en}, "
    }

    val cityName = viewModel?.locationModel?.value?.result?.get(0)?.city?.name?.en

    val locationAddress = "$subAreaName$areaName$cityName"

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 14.dp, start = 16.dp, end = 16.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(com.wemeal.R.drawable.ic_location),
            contentDescription = "",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(end = 10.dp)
                .height(21.9.dp)
                .width(18.dp)
        )
        Text(
            text = locationAddress ?: "",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(
                    visible = isLoading,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                ),
            maxLines = 1,
            color = darkBlack,
            letterSpacing = TextUnit(0f, TextUnitType.Sp),
            fontSize = 14.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratBold)
            )
        )
    }
}