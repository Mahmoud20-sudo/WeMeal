package com.wemeal.presentation.onboarding.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.util.*

@ExperimentalUnitApi
@Preview
@Composable
fun PreviewEmptyStateView() {
    EmptyStateView("")
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun EmptyStateView(
    querySearch: String,
    imageId: Int = R.drawable.ic_no_result,
    isLocationSearch: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(top = 95.dp, start = 71.dp, end = 71.dp)
                .fillMaxWidth()
                .height(198.dp),
            painter = painterResource(id = imageId),
            contentDescription = "NO DATA"
        )
        Text(
            modifier = Modifier.padding(top = 31.dp),
            text = stringResource(id = R.string.no_result),
            maxLines = 1,
            color = black,
            fontSize = 16.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratBold)
            )
        )
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = "${stringResource(id = R.string.no_match)}  \"${querySearch}\"",
            maxLines = 1,
            color = black,
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratMedium)
            )
        )
        AnimatedVisibility(visible = isLocationSearch) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 38.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .gradientBackground(
                        listOf(
                            regularBlue,
                            lightPurple
                        ), angle = 360f,
                        CornerRadius(60f, 60f)
                    )
                    .clickable(
                        enabled = true,
                        onClickLabel = "DISMISS DIALOG",
                        onClick = {
                            onClick()
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 9.dp)
                        .size(22.dp),
                    painter = painterResource(id = R.drawable.ic_detect_location_no_bg),
                    contentDescription = "NO DATA"
                )
                Text(
                    modifier = Modifier.padding(end = 15.dp),
                    text = stringResource(id = R.string.use_current_location),
                    maxLines = 1,
                    color = Color.White,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontFamily = FontFamily(montserratSemiBold)
                    )
                )
            }
        }
    }
}