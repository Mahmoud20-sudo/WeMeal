package com.wemeal.presentation.main.components.details

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
fun PreviewDetailsTopBar() {
    DetailsTopBar("") {}
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailsTopBar(name: String, onBackClick: () -> Unit) {
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
//                context.logMultipleEvents(
//                    CustomEvent.USER_ESCAPE_POST_DETAILS_PAGE,
//                    foodiesList[index]
//                                         )
            }
            Spacer(modifier = Modifier.width(20.3.dp))
//            Text(
//                text = "${foodiesList[index].name}${stringResource(id = R.string.post)}",
//                color = black300,
//                fontSize = 16.sp,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                textAlign = TextAlign.Center,
//                style = TextStyle(
//                    fontWeight = FontWeight.SemiBold
//                )
//            )
            Text(
                text = name,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratSemiBold),
                    fontSize = 16.sp,
                    color = black300
                ),
                modifier = Modifier
            )
        }
    }
}