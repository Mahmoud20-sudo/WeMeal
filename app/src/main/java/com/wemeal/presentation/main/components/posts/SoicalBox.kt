package com.wemeal.presentation.main.components.posts

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wemeal.R
import com.wemeal.data.model.main.feed.Activity
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.extensions.loggingPostAction
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.shared.DrawableWrapper
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import com.wemeal.presentation.util.montserratMedium

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewSocialBox() {
    SocialBox(
        feedModel = null,
        onLikeClick = {  }
    )
}

@Composable
fun SocialBox(
    feedModel: Activity?,
    onLikeClick: (feedModel: Activity?) -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        Modifier.fillMaxWidth()
    ) {
        DrawableWrapper(
            drawableEnd = if (feedModel?.isLikedMutable?.value == true) R.drawable.ic_like_active else R.drawable.ic_like,
            width = 20.dp,
            height = 18.dp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    enabled = true,
                    onClickLabel = "End Search",
                    onClick = {
                        setLikeAction(context, feedModel)
                        onLikeClick.invoke(feedModel)
                    }
                )
        ) {
            SocialText(text = feedModel?.likeCountMutable?.value)
        }
        DrawableWrapper(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(end = 10.dp),
            drawableEnd = R.drawable.ic_comment,
            width = 20.dp,
            height = 20.dp
        ) {
            SocialText(text = feedModel?.comments?.size)
        }
        DrawableWrapper(
            modifier = Modifier.align(Alignment.CenterEnd),
            drawableEnd = R.drawable.ic_share,
            width = 18.dp,
            height = 15.dp
        ) {
            SocialText(text = feedModel?.shareCountMutable?.value)
        }
    }
}

fun setLikeAction(
    context: Context,
    feedModel:Activity?
) {
    feedModel?.likeCountMutable?.value = when (feedModel?.isLikedMutable?.value) {
        true -> feedModel.likeCountMutable?.value?.minus(1)
        else -> feedModel?.likeCountMutable?.value?.plus(1)
    }

    feedModel?.isLikedMutable?.value = !(feedModel?.isLikedMutable?.value ?: false)
}

@Composable
private fun SocialText(text: Int?) {
    Text(
        text = if(text?.compareTo(0) == 1) "$text" else "",
        fontSize = 14.sp,
        color = black300,
        fontFamily = FontFamily(montserratMedium)
    )
}