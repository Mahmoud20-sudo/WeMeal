package com.wemeal.presentation.intro

import android.graphics.Rect
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sqrt
import android.graphics.RectF




// Set of Material typography styles to start with
val typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)

fun Modifier.gradientBackground(
    colors: List<Color>, angle: Float,
    corners: CornerRadius, alpha: Float = 1.0f
) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = kotlin.math.cos(angleRad).toFloat() //Fractional x
        val y = kotlin.math.sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = offset.x.coerceAtLeast(0f).coerceAtMost(size.width),
            y = size.height - offset.y.coerceAtLeast(0f).coerceAtMost(size.height)
        )

        drawRoundRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            cornerRadius = corners,
            size = size,
            alpha = alpha
        )
    }
)