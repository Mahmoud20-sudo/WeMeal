package com.wemeal.presentation.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.util.montserratSemiBold

@Preview
@Composable
fun PreviewTextWithDrawable() {
    TextWithDrawable(
        text = "TEST as;ldjfyatyu;dilafsd,yuau;lsdkafdailksdanskdlalhsda",
        texSize = 14,
        textColor = black300,
        resourceId = R.drawable.ic_verified,
        drawableSize = 15,
        drawablePadding = 8,
        shape = CircleShape,
        linesNumber = 1,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TextWithDrawable(
    text: String,
    texSize: Int,
    textColor: androidx.compose.ui.graphics.Color,
    resourceId: Int,
    drawableSize: Int,
    drawablePadding: Int,
    shape: Shape,
    linesNumber: Int,
    fontWeight: FontWeight,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    onClick()
                }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        Text(
            text = text,
            maxLines = linesNumber,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            fontSize = texSize.sp,
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontWeight = fontWeight,
                fontFamily = FontFamily(montserratSemiBold)
            ),
            modifier = Modifier.weight(3f, false)
        )
        if (resourceId > 0) {
            Image(
                painter = painterResource(resourceId),
                contentDescription = "verify",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .padding(start = drawablePadding.dp)
                    .weight(0.8f, false)
                    .size(drawableSize.dp)
                    .clip(shape) // clip to the circle shape
            )
        }
    }
}