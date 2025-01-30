package com.wemeal.presentation.main.components.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.lightGray200
import com.wemeal.presentation.intro.purple300
import com.wemeal.presentation.onboarding.components.text.InputEditText
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratRegular

@Preview
@Composable
fun PreviewAddCommentCard() {
    AddCommentCard(modifier = Modifier)
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AddCommentCard(modifier: Modifier) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        modifier = modifier
            .height(56.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_temp),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)                       // clip to the circle shape
            )
            InputEditText(
                value = "",
                placeHolderString = stringResource(id = R.string.write_comment),
                modifier = Modifier
                    .background(
                        color = lightGray200.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .weight(1f)
                    .height(32.dp)
                    .padding(start = 14.dp, end = 14.dp),
                onValueChange = {

                },
                maxLines = 1,
                contentTextStyle = TextStyle(
                    fontSize = 12.sp,
                    color = black300,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratRegular)
                ),
                hintTextStyle = TextStyle(
                    color = black300.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratMedium)
                )
            )
            Text(
                text = stringResource(id = R.string.rules),
                fontSize = 12.sp,
                color = purple300,
                letterSpacing = TextUnit(-0.42f, TextUnitType.Sp),
                modifier = Modifier
                    .padding(start = 7.dp, end = 16.dp),
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratMedium)
            )
        }
    }

}