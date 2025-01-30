package com.wemeal.presentation.main.components.deletion

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun PreviewDeletionBox() {
    DeletionBox {}
}

@Composable
fun DeletionBox(refreshClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 0.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_deletion_holder),
            contentDescription = "",            // crop the image if it's not a square
            modifier = Modifier
                .width(159.dp)
                .height(128.dp)                    // clip to the circle shape
        )
        Text(
            text = stringResource(id = R.string.deletion_title),
            style = TextStyle(
                fontFamily = FontFamily(montserratSemiBold),
                fontSize = 14.sp,
                color = black300
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 28.dp)
        )
        Row{
            Text(
                text = context.getString(R.string.deletion_message),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratRegular),
                    fontSize = 12.sp,
                    color = black300
                ),
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            ClickableText(
                text = context.getString(R.string.refresh),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratRegular),
                    fontSize = 12.sp,
                    color = purple600
                ),
                modifier = Modifier.padding(top = 8.dp, start = 4.dp)
            ) {
                refreshClick.invoke()
            }
        }
    }
}