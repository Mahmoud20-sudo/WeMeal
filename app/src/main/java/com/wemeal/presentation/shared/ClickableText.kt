package com.wemeal.presentation.shared

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.wemeal.R
import com.wemeal.presentation.intro.purple300
import com.wemeal.presentation.util.ImageActions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.wemeal.presentation.intro.purple600

@Preview
@Composable
fun PreviewClickableText() {
    ClickableText(
        text = "TEST",
        color = purple600,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        modifier = Modifier
    ) {}
}

@Composable
fun ClickableText(
    text: String,
    color: Color = purple600,
    modifier: Modifier,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Center,
    overflow: TextOverflow = TextOverflow.Visible,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier.clickable(
            enabled = true,
            onClickLabel = "",
            onClick = {
                onClick()
            }),
        text = text,
        textAlign = textAlign,
        maxLines = 1,
        color = color,
        style = style,
        overflow = overflow
    )
}