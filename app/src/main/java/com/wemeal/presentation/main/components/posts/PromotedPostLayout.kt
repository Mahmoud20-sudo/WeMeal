package com.wemeal.presentation.main.components.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.grey
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratRegular

@Preview
@Composable
fun PreviewPromotedPostRow() {
    PromotedPostLayout("This restaurant has been ordered from many times") {}
}

@Composable
fun PromotedPostLayout(reason: String, onMoreActionClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
    ) {
        val (titleText, moreImg, divider) = createRefs()

        Text(
            text = reason,
            maxLines = 1,
            color = black300.copy(alpha = 0.7f),
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular)
            ),
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(moreImg.start)
                }
                .fillMaxWidth()
                .padding(top = 8.dp, start = 30.dp, end = 30.dp)
        )

        ClickableImage(
            drawableId = R.drawable.ic_more,
            imageActions = ImageActions.MORE,
            modifier = Modifier
                .constrainAs(moreImg) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 16.dp)
                .size(25.dp)
                .clip(CircleShape)
                .background(Color.Transparent),
            contentScale = ContentScale.Inside
        ) {
            onMoreActionClick()
        }

        Spacer(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(titleText.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .padding(top = 7.dp, bottom = 3.8.dp)
                .background(grey.copy(alpha = 0.5f))
                .height(0.5.dp)

        )
    }
}