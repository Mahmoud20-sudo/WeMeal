package com.wemeal.presentation.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.wemeal.presentation.extensions.shortToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ZoomableImage(
    painter: Painter,
    maxScale: Float = .30f,
    minScale: Float = 3f,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    isRotation: Boolean = false,
    isZoomable: Boolean = true,
    onZoomGestureDetected: (isZoomIn: Boolean) -> Unit
) {
    val scale = remember { mutableStateOf(1f) }
    var scaleForLogging = 0f
    val rotationState = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(1f) }
    val offsetY = remember { mutableStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
//            .clip(RectangleShape)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                if (isZoomable) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()
                            do {
                                val event = awaitPointerEvent()
                                scale.value *= event.calculateZoom()
                                if (scale.value > 1) {//zoom in
                                    val offset = event.calculatePan()
                                    offsetX.value += offset.x
                                    offsetY.value += offset.y
//                                    rotationState.value += event.calculateRotation()
                                } else {//zoom out
                                    scale.value = 1f
                                    offsetX.value = 1f
                                    offsetY.value = 1f
                                }
                                when {
                                    scale.value > scaleForLogging -> onZoomGestureDetected(true)
                                    scale.value < scaleForLogging -> onZoomGestureDetected(false)
                                }
                                scaleForLogging = scale.value
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
            }

    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = contentScale,
            alignment = alignment,
            modifier = modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    if (isZoomable) {
                        scaleX = maxOf(maxScale, minOf(minScale, scale.value))
                        scaleY = maxOf(maxScale, minOf(minScale, scale.value))
                        if (isRotation) {
                            rotationZ = rotationState.value
                        }
//                        translationX = offsetX.value
//                        translationY = offsetY.value
                    }
                }
        )
    }
}