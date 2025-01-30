package com.wemeal.presentation.main.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.lightGray200
import com.wemeal.presentation.onboarding.components.text.InputEditText
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.montserratMedium

@Preview
@Composable
fun PreviewCreatePostRow() {
    CreatePostRow {}
}

@Composable
fun CreatePostRow(onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_temp),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)                       // clip to the circle shape
        )
        Card(
            elevation = 3.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .padding(start = 12.dp)
                .clickable(
                    enabled = true,
                    onClickLabel = "CREATE POST",
                    onClick = {
                        onClick.invoke()
                    }
                ),
        ) {
            Text(
                text = stringResource(id = R.string.share_ur_experience),
                color = black300.copy(alpha = 0.5f),
                style = TextStyle(
                    fontFamily = FontFamily(montserratMedium),
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .padding(start = 16.dp, top = 11.dp, bottom = 11.dp)
            )
        }
    }
}