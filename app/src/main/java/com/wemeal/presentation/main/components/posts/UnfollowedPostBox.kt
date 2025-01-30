package com.wemeal.presentation.main.components.posts

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
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
import com.wemeal.data.model.FoodyModel
import com.wemeal.data.model.main.feed.Activity
import com.wemeal.data.model.main.feed.FeedModel
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.purple600
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewUnfollowedPostBox() {
    UnfollowedPostBox(feedModel = null){}
}

@Composable
fun UnfollowedPostBox(feedModel: Activity?, onFollowClick : (feedModel: Activity?) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .padding(bottom = 3.0.dp)
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        val (followImg, followTitle, undoText, followMessage) = createRefs()


        Image(
            painter = painterResource(R.drawable.ic_unfollow_promot),
            contentDescription = "",            // crop the image if it's not a square
            modifier = Modifier
                .width(30.dp)
                .height(27.dp)                    // clip to the circle shape
                .constrainAs(followImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = "${stringResource(id = R.string.unfollowed_title)} ${feedModel?.user?.firstName}",
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold),
                fontSize = 12.sp,
                color = black300
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 3.dp)
                .constrainAs(followTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(followImg.end)
                }
        )
        ClickableText(
            text = stringResource(id = R.string.undo),
            color = purple600,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontFamily = FontFamily(montserratSemiBold)
            ),
            modifier = Modifier
                .constrainAs(undoText) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(start = 8.dp, top = 5.dp)
        ) {
            onFollowClick.invoke(feedModel)
        }
        Text(
            text = "${stringResource(id = R.string.message_one)} ${feedModel?.user?.firstName} ${
                stringResource(
                    id = R.string.posts_small
                )
            } ${stringResource(id = R.string.message_two)}",
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular),
                fontSize = 12.sp,
                color = black300
            ),
            modifier = Modifier
                .constrainAs(followMessage) {
                    top.linkTo(followImg.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 8.dp)
        )
    }
}