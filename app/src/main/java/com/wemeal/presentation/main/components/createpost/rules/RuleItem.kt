package com.wemeal.presentation.main.components.createpost.rules

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.util.appleColorEmjoi
import com.wemeal.presentation.util.montserratMedium

@SuppressLint("UnrememberedMutableState")
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewRuleItem() {
    RuleItem("The content you are sharing should be food related.",
        "\uD83E\uDD78")
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun RuleItem(itemName: String, itemIconHex : String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemIconHex,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = FontFamily(
                    appleColorEmjoi),
                fontSize = 16.sp
            ),
            modifier = Modifier
                .gradientBackground(
                    listOf(
                        regularBlue,
                        lightPurple
                    ), angle = 360f,
                    CornerRadius(60f, 60f),
                    alpha = 0.1f
                )
                .size(44.dp)
                .padding(top = 10.dp)
                .clip(CircleShape)                       // clip to the circle shape
        )
        Text(
            text = itemName,
            style = TextStyle(
                fontFamily = FontFamily(
                    montserratMedium),
                fontSize = 12.sp,
                color = black300
            ),
            modifier = Modifier
                .padding(start = 8.dp)//, top = 15.dp
                .weight(1.0f)
        )
    }
}