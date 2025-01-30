package com.wemeal.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.presentation.intro.darkBlack
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.montserratMedium

@Preview
@Composable
fun PreviewRoundedText() {
    RoundedText(null){}
}

@Composable
fun RoundedText(result: com.wemeal.data.model.onboarding.nearest.Result?, onClick: (result: com.wemeal.data.model.onboarding.nearest.Result) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 8.dp, end = 8.dp)
            .height(32.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(start = 14.dp, end = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(
            text = result?.name?.en ?: "",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(montserratMedium)
            ),
            color = darkBlack,
            modifier = Modifier.placeholder(
                visible = false,
                color = lightSky,
                // optional, defaults to RectangleShape
                shape = RoundedCornerShape(16.dp),
                highlight = PlaceholderHighlight.fade(
                    highlightColor = Color.White,
                ),
            )
        ){
            result?.let { onClick.invoke(it) }
        }
    }
}