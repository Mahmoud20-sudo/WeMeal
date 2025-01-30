package com.wemeal.presentation.main.components.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.purple300
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratSemiBold

@Preview
@Composable
fun PreviewCreatePostTopBar() {
    CreatePostTopBar(Modifier, false, {})
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun CreatePostTopBar(
    modifier: Modifier,
    isEnabled: Boolean,
    onBackClick: () -> Unit,
    onPostClick: () -> Unit = {},
    @StringRes buttonId: Int = R.string._post,
    @StringRes screenId: Int = R.string.create_post
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 9.dp,
            bottomEnd = 9.dp
        ),
        elevation = 5.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableImage(
                drawableId = R.drawable.ic_bar_back,
                imageActions = ImageActions.BACK,
                modifier = Modifier
                    .size(35.dp)
//                    .padding(5.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Inside
            ) {
                onBackClick()
            }
            Spacer(modifier = Modifier.width(18.3.dp))
            Text(
                text = stringResource(screenId),
                color = black300,
                fontSize = 16.sp,
                maxLines = 1,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontFamily = FontFamily(montserratSemiBold)
                ),
                modifier = Modifier.weight(1f)
            )
            ClickableText(
                text = stringResource(id = buttonId),
                color = if (isEnabled) purple300 else purple300.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier,
                style = TextStyle(
                    fontFamily = FontFamily(montserratSemiBold),
                    fontSize = 16.sp,
                    letterSpacing = TextUnit(-0.03f, TextUnitType.Sp)
                )
            ) {
                if (isEnabled)
                    onPostClick.invoke()
            }
        }
    }
}