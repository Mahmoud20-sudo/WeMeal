package com.wemeal.presentation.shared

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import com.wemeal.presentation.util.ImageActions

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CoilImage(
    painter: ImagePainter,
    @DrawableRes placeholder: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    onImageClick: () -> Unit = {},
) {
    Box(modifier.fillMaxSize()) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = painter.state is ImagePainter.State.Error
        ) {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = contentDescription,
//                alignment = alignment,
                contentScale = ContentScale.FillBounds,
                alpha = alpha,
//                colorFilter = colorFilter,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}