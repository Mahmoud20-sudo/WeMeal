package com.wemeal.presentation.shared

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import com.wemeal.presentation.util.ImageActions

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ClickableCoilImage(
    painter: ImagePainter,
    @DrawableRes placeholder: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    onImageClick: () -> Unit = {},
    onCloseClick: () -> Unit = {}
) {
    Box(modifier.clickable(
        enabled = true,
        onClickLabel = ImageActions.CLOSE.clickLabel,
        onClick = {
            onImageClick.invoke()
        }
    )) {
//        Image(
//            painter = painter,
//            contentDescription = contentDescription,
//            alignment = alignment,
//            contentScale = contentScale,
//            alpha = alpha,
//            colorFilter = colorFilter,
////            modifier = modifier.matchParentSize()
//        )

        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            alignment = alignment,
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = placeholder != 0, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = ImageActions.CLOSE.contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .clickable(
                        enabled = true,
                        onClickLabel = ImageActions.CLOSE.clickLabel,
                        onClick = {
                            onCloseClick.invoke()
                        }
                    )
            )
        }
    }
}