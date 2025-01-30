package com.wemeal.presentation.shared

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wemeal.R
import com.wemeal.presentation.util.ImageActions

@Preview
@Composable
fun PreviewCloseImage() {
    CloseImage(R.drawable.ic_clear_dark, Modifier) {}
}

@Composable
fun CloseImage(
    @DrawableRes img: Int,
    modifier: Modifier,
    onCloseClick: () -> Unit
) {
    ClickableImage(
        drawableId = img,
        imageActions = ImageActions.CLOSE,
        contentScale = ContentScale.Inside,
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
    ) {
        onCloseClick.invoke()
    }
}