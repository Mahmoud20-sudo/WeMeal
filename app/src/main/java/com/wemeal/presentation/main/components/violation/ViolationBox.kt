package com.wemeal.presentation.main.components.violation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.purple600
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@Preview
@Composable
fun PreviewViolationBox() {
    ViolationBox({}, {}, {})
}

@Composable
fun ViolationBox(
    onDismissClick: () -> Unit,
    rulesClick: () -> Unit,
    createPostClick: () -> Unit
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 3.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        val (warningImg, warningText, closeIcon, messageText, rulesText, createText) = createRefs()

        Image(
            painter = painterResource(R.drawable.ic_violate),
            contentDescription = "",            // crop the image if it's not a square
            modifier = Modifier
                .width(19.1.dp)
                .height(17.dp)                    // clip to the circle shape
                .constrainAs(warningImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = stringResource(id = R.string.violation_title),
            style = TextStyle(
                fontFamily = FontFamily(montserratSemiBold),
                fontSize = 14.sp,
                color = black300
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 0.dp)
                .constrainAs(warningText) {
                    top.linkTo(parent.top)
                    start.linkTo(warningImg.end)
                }
        )
        ClickableImage(
            drawableId = R.drawable.ic_clear_opacity,
            imageActions = ImageActions.CLOSE,
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .constrainAs(closeIcon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            onDismissClick.invoke()
        }
        Text(
            text = context.getString(R.string.violation_message),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular),
                fontSize = 12.sp,
                color = black300
            ),
            modifier = Modifier
                .constrainAs(messageText) {
                    top.linkTo(warningImg.bottom)
                    start.linkTo(parent.start)
                }
                .padding(top = 8.dp)
        )
        ClickableText(
            text = context.getString(R.string.community_rules),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular),
                fontSize = 12.sp,
                color = purple600
            ),
            modifier = Modifier
                .constrainAs(rulesText) {
                    top.linkTo(warningImg.bottom)
                    start.linkTo(messageText.end)
                }
                .padding(top = 8.dp, start = 4.dp)
        ) {
            rulesClick.invoke()
        }
        ClickableText(
            text = context.getString(R.string.create_new_post),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold),
                fontSize = 14.sp,
                color = purple600
            ),
            modifier = Modifier
                .constrainAs(createText) {
                    top.linkTo(rulesText.bottom)
                    end.linkTo(parent.end)
                }
                .padding(top = 18.dp)
        ) {
            createPostClick.invoke()
        }
    }
}