package com.wemeal.presentation.onboarding.components.text

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.util.montserratBold

@ExperimentalUnitApi
@Preview
@Composable
fun PreviewMapInfo() {
    MapInfoText()
}

@ExperimentalUnitApi
@Composable
fun MapInfoText() {
    Box(
        modifier = Modifier
            .padding(bottom = 150.dp)
            .gradientBackground(
                listOf(
                    lightBlue,
                    lightPink
                ), angle = 360f,
                CornerRadius(40f, 40f)
            )
            .fillMaxWidth()
            .height(31.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.drag_map),
            letterSpacing = TextUnit(2f, TextUnitType.Sp),
            lineHeight = TextUnit(13.3f, TextUnitType.Sp),
            fontSize = 10.sp,
            maxLines = 1,
            color = purple300,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratBold)
            )
        )
    }
}