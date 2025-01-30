package com.wemeal.presentation.main.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.purple600
import com.wemeal.presentation.shared.ClickableText

@Preview
@Composable
fun PreviewDeletedPostBox() {
    DeletedPostBox(onRefreshClick = {})
}

@Composable
fun DeletedPostBox(onRefreshClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .padding(bottom = 3.0.dp)
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, bottom = 15.dp)
    ) {
        val (deleteImg, deleteTitle, refreshText, deleteMessage) = createRefs()

        Image(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = "",            // crop the image if it's not a square
            modifier = Modifier
                .padding(top = 8.dp)
                .width(27.dp)
                .height(27.dp)                    // clip to the circle shape
                .constrainAs(deleteImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = stringResource(id = R.string.delete_title),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = black300
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 14.dp)
                .constrainAs(deleteTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(deleteImg.end)
                }
        )
        ClickableText(
            text = stringResource(id = R.string.refresh),
            color = purple600,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            ),
            modifier = Modifier
                .constrainAs(refreshText) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(start = 8.dp, top = 13.dp)
        ) {
            onRefreshClick()
        }
        Text(
            text = stringResource(id = R.string.delete_message),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = black300
            ),
            modifier = Modifier
                .constrainAs(deleteMessage) {
                    top.linkTo(deleteImg.bottom)
                    start.linkTo(parent.start)
                }
                .padding(top = 8.dp)
        )
    }
}