package com.wemeal.presentation.main.components.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wemeal.R
import com.wemeal.presentation.extensions.logMultipleEvents
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.foodiesList
import com.wemeal.presentation.util.montserratSemiBold

@Preview
@Composable
fun PreviewReportTopBar() {
    ReportTopBar("TEST", -1, false) {}
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReportTopBar(
    title: String,
    index: Int = -1, isReport: Boolean = false, onBackClick: () -> Unit
) {
    val context = LocalContext.current

    TopAppBar(
        modifier = Modifier.height(56.dp),
        backgroundColor = Color.White,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableImage(
                drawableId = R.drawable.ic_bar_back,
                imageActions = ImageActions.BACK,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Inside
            ) {
                onBackClick()
                if (isReport)
                    context.logMultipleEvents(
                        CustomEvent.USER_EXIT_REPORT_POST_SCREEN,
                        foodiesList[index]
                                             )
            }
            Spacer(modifier = Modifier.width(20.3.dp))
            Text(
                text = title,
                color = black300,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratSemiBold)
                )
            )
        }
    }
}