package com.wemeal.presentation.shared

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Typeface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import  androidx.compose.foundation.Canvas
import androidx.compose.ui.tooling.preview.Preview
import com.wemeal.presentation.intro.lightPurple
import com.wemeal.presentation.intro.purple800
import com.wemeal.presentation.intro.purple900

@Preview
@Composable
fun PreviewDrawGradientText() {
    DrawGradientText(startColor = purple800, endColor = purple900, name = "LOGO")
}

@Composable
fun DrawGradientText(
    startColor: Color, endColor: Color,
    name: String, modifier: Modifier = Modifier) {

    val paint = Paint().asFrameworkPaint()

    val gradientShader: Shader = LinearGradientShader(
        from = Offset(0f, 0f),
        to = Offset(50f, 50f),
        listOf(startColor, endColor)
    )

    Canvas(modifier.fillMaxSize()) {
        paint.apply {
            isAntiAlias = true
            textSize = 50f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            style = android.graphics.Paint.Style.FILL
//            color = android.graphics.Color.parseColor("#cdcdcd")
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
//            maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
        }
        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.nativeCanvas.translate(2f, 5f)
            canvas.nativeCanvas.drawText(name, 0f, 50f, paint)
            canvas.restore()
            paint.shader = gradientShader
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            paint.maskFilter = null
            canvas.nativeCanvas.drawText(name, 0f, 50f, paint)
            canvas.nativeCanvas.translate(2f, 5f)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
//            paint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
            canvas.nativeCanvas.drawText(name, 0f, 50f, paint)
        }
        paint.reset()
    }
}