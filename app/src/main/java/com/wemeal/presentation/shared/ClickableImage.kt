package com.wemeal.presentation.shared

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wemeal.R
import com.wemeal.presentation.util.ImageActions

@Preview
@Composable
fun PreviewClickableImage() {
    ClickableImage(R.drawable.ic_more, ImageActions.CLOSE, modifier = Modifier,
        ContentScale.Crop,
    ) {}
}
//////////////////////////////////////////////
@Composable
fun ClickableImage(
    @DrawableRes drawableId: Int,
    imageActions: ImageActions,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(drawableId),
        contentDescription = imageActions.contentDescription,
        contentScale = contentScale,            // crop the image if it's not a square
        modifier = modifier.clickable(
            enabled = true,
            onClickLabel = imageActions.clickLabel,
            onClick = {
                onClick()
            }
        ),alignment = alignment
    )
}