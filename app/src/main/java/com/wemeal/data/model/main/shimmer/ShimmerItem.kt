package com.wemeal.data.model.main.shimmer

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.ImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.intro.lightSky100
import com.wemeal.presentation.shared.ClickableCoilImage
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.shared.TextWithDrawable
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratRegular

@Composable
fun ShimmerItem(
//    brush: Brush
) {
// Column composable containing spacer shaped like a rectangle,
// set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
// Composable which is the Animation you are gonna create.
    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(bottom = 17.5.dp),
    ) {
        val (userImage, userNameText, dateTimeText, postImage, categoryBox, socialBox, commentBox) = createRefs()
        //===========================This part shared btw different types========================
        Spacer(
            modifier = Modifier
                .padding(
                    top = 12.dp, start = 16.dp, bottom = 8.dp
                )
                .size(40.dp)
                .clip(CircleShape)
                .placeholder(
                    visible = true,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )// clip to the circle shape
                .constrainAs(userImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Spacer(
            modifier = Modifier
                .padding(start = 8.dp, top = 21.dp)
                .constrainAs(userNameText) {
                    top.linkTo(parent.top)
                    start.linkTo(userImage.end)
                }
                .clip(RoundedCornerShape(4.dp))
                .width(119.dp)
                .height(10.dp)
                .placeholder(
                    visible = true,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )// clip to the circle shape
        )

        Spacer(
            modifier = Modifier
                .padding(start = 8.dp, top = 9.dp)
                .clip(RoundedCornerShape(4.dp))
                .width(64.dp)
                .height(6.dp)
                .placeholder(
                    visible = true,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )// clip to the circle shape
                .constrainAs(dateTimeText) {
                    top.linkTo(userNameText.bottom)
                    start.linkTo(userImage.end)
                },
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
                .placeholder(
                    visible = true,
                    color = lightSky100,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
                .constrainAs(postImage) {
                    top.linkTo(userImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        Spacer(
            modifier = Modifier
                .padding(start = 16.dp, top = 15.dp)
                .width(51.dp)
                .height(10.dp)
                .placeholder(
                    visible = true,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
                .constrainAs(socialBox) {
                    top.linkTo(postImage.bottom)
                    start.linkTo(parent.start)
                },
        )

        Spacer(
            modifier = Modifier
                .padding(start = 16.dp, top = 15.dp)
                .width(51.dp)
                .height(10.dp)
                .placeholder(
                    visible = true,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
                .constrainAs(commentBox) {
                    top.linkTo(postImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        Spacer(
            modifier = Modifier
                .padding(end = 16.dp, top = 15.dp)
                .width(51.dp)
                .height(10.dp)
                .placeholder(
                    visible = true,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
                .constrainAs(categoryBox) {
                    top.linkTo(postImage.bottom)
                    end.linkTo(parent.end)
                },
        )
    }
}
