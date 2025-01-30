package com.wemeal.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun RoundedBox(opacity: Float) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp, end = 8.dp)
            .width(46.dp)
            .height(7.dp)
            .background(
                Color.White.copy(alpha = opacity),
                shape = RoundedCornerShape(4.dp)
            ),
    )
}