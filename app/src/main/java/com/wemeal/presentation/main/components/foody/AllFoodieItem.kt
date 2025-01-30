package com.wemeal.presentation.main.components.foody

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.data.model.FoodyModel
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.onboarding.components.follow.FoodieFollowingBtn
import com.wemeal.presentation.util.montserratSemiBold

@SuppressLint("UnrememberedMutableState")
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewAllFoodyItem() {
    AllFoodyItem(
        foodyModel =
        FoodyModel(
            id = 1, firstName = "Mariam", lastName = "Emad",
            img = R.mipmap.ic_chicken_img,
            cover = R.mipmap.ic_cockdoor_cover,
            followersCount = 8.2f,
            postsCount = 235,
            isVerified = true,
            isUnFollowed = mutableStateOf(false)
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun AllFoodyItem(
    foodyModel: FoodyModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_temp),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .size(56.dp)
                .clip(CircleShape)                       // clip to the circle shape
        )
        Text(
            text = "${foodyModel.firstName} ${foodyModel.lastName}",
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold),
                fontSize = 16.sp,
                letterSpacing = TextUnit(-0.49f, TextUnitType.Sp),
                color = darkGrey
            ),
            modifier = Modifier
                .padding(start = 8.dp)//, top = 15.dp
                .weight(1.0f)
        )
        FoodieFollowingBtn(
            modifier = Modifier.padding(start = 8.dp ),//, top = 15.dp
            isFollowed = foodyModel.isFollowed, checkCount = { }
        )
    }
}