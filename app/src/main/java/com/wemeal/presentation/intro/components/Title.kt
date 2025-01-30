package com.wemeal.presentation.intro.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.wemeal.presentation.util.montserratMedium

@ExperimentalUnitApi
@Preview(name = "Title")
@Composable
fun previewTitle() {
    Title(text = "Hola Ann", FontFamily(montserratMedium))
}

@ExperimentalUnitApi
@Composable
fun Title(
    text: String,
    fontFamily: FontFamily, modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 24.sp,
        letterSpacing = TextUnit(-0.84f, TextUnitType.Sp),
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontWeight = FontWeight.Normal
        ),
        modifier = modifier
    )
}