package com.wemeal.presentation.main.components.posts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.data.model.Comment
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.lightGray
import com.wemeal.presentation.intro.lightGray200
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@Preview
@Composable
fun PreviewCommentsBox() {
    CommentsBox(
        comment = Comment(
            1,
            "Mahmoud Mohamed",
            R.mipmap.avatar_copy_3,
            "Lorem ipsum ",
            replies = listOf(Comment(55, "Macdonald's", R.mipmap.ic_mac_img, "WOOW"))
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CommentsItem(comment: Comment) {
    Column {
        CommentsBox(comment = comment)
        AnimatedVisibility(
            modifier = Modifier
                .padding(top = 12.dp, start = 65.dp),
            visible = !comment.replies.isNullOrEmpty()
        ) {
            comment.replies.forEach { reply ->
                CommentsBox(comment = reply)
            }
        }
    }
}

@Composable
private fun CommentsBox(comment: Comment) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 7.5.dp)
    ) {
        // Create references for the composables to constrain
        val (userImage, textContainer, dateText, likeText, replyText, likeCountsText, likeCountImage) = createRefs()
        Image(
            painter = painterResource(comment.drawable),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .constrainAs(userImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(40.dp)
                .clip(CircleShape)                       // clip to the circle shape
        )

        Column(modifier = Modifier
            .padding(start = 8.dp)
            .background(lightGray200.copy(alpha = 0.5f), shape = RoundedCornerShape(13.dp))
            .constrainAs(textContainer) {
                top.linkTo(parent.top)
                start.linkTo(userImage.end)
            }
            .padding(vertical = 8.dp, horizontal = 12.dp)
        )
        {
            Text(
                text = comment.userName,
                fontSize = 12.sp,
                color = black300,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold)
            )
            Text(
                text = comment.comment,
                fontSize = 12.sp,
                color = black300,
                fontFamily = FontFamily(montserratRegular)
            )
        }
        Text(
            text = "1h",
            fontSize = 12.sp,
            color = black300,
            modifier = Modifier
                .constrainAs(dateText) {
                    top.linkTo(textContainer.bottom)
                    start.linkTo(userImage.end)
                }
                .padding(top = 2.dp, start = 13.dp),
            fontFamily = FontFamily(montserratRegular)
        )
        Text(
            text = stringResource(id = R.string.like_hint),
            fontSize = 12.sp,
            color = black300,
            modifier = Modifier
                .constrainAs(likeText) {
                    top.linkTo(textContainer.bottom)
                    start.linkTo(dateText.end)
                }
                .padding(top = 2.dp, start = 16.dp),
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(montserratMedium)
        )
        Text(
            text = stringResource(id = R.string.reply_hint),
            fontSize = 12.sp,
            color = black300,
            modifier = Modifier
                .constrainAs(replyText) {
                    top.linkTo(textContainer.bottom)
                    start.linkTo(likeText.end)
                }
                .padding(top = 2.dp, start = 17.dp),
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(montserratMedium)
        )
        Text(
            text = "2",
            fontSize = 12.sp,
            color = black300,
            modifier = Modifier
                .constrainAs(likeCountsText) {
                    top.linkTo(textContainer.bottom)
                    start.linkTo(replyText.end)
                }
                .padding(top = 2.dp, start = 16.dp),
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(montserratMedium)
        )
        Image(
            painter = painterResource(R.drawable.ic_likes_count),
            contentDescription = "likes_count",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .constrainAs(likeCountImage) {
                    top.linkTo(textContainer.bottom)
                    start.linkTo(likeCountsText.end)
                }
                .padding(top = 2.dp, start = 4.dp)
                .size(16.dp)
                .clip(CircleShape)                       // clip to the circle shape
        )
    }

//    Box(Modifier.fillMaxWidth()) {
//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 9.dp)
//                .background(grey.copy(alpha = 0.3f))
//                .height(1.dp)
//        )
//    }
}
