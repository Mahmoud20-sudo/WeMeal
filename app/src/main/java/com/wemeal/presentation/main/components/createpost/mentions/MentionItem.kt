package com.wemeal.presentation.main.components.createpost.mentions

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.CoilImage
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratRegular

@ExperimentalCoilApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewMentionItem() {
    MentionItem(
        modifier = Modifier,
        isShimmerEffect = false,
        user = null
    )
}

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun MentionItem(
    modifier: Modifier,
    isShimmerEffect: Boolean = false,
    user: com.wemeal.data.model.main.Result?
) {
    val imagePainter = rememberImagePainter(
        data = user?.profilePictureUrl ?: "https://www.example.com/image.jpg",
        builder = {
            transformations(CircleCropTransformation())
        })
    val imageState = imagePainter.state
    val userFullName = if (user == null) "" else "${user.firstName} ${user.lastName}"

    Row(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        CoilImage(
            painter = imagePainter,
            placeholder = R.drawable.ic_cover_placeholder,
            contentDescription = "COVER",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(CircleShape)
                .size(28.dp)
                .placeholder(
                    visible = isShimmerEffect || imageState is ImagePainter.State.Loading,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = userFullName,
                style = TextStyle(
                    fontFamily = FontFamily(
                        montserratMedium
                    ),
                    fontSize = 12.sp,
                    color = black300,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = isShimmerEffect,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    )
            )
            Text(
                text = "@${user?.username}",
                style = TextStyle(
                    fontFamily = FontFamily(
                        montserratRegular
                    ),
                    fontSize = 10.sp,
                    color = black300
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .placeholder(
                        visible = isShimmerEffect,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    )
            )
        }

    }
}