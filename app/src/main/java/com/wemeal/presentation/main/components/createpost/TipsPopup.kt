package com.wemeal.presentation.main.components.createpost

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.CloseImage
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratBold
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewPopupWindow() {
    PopupWindow(true) {}
}

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun PopupWindow(
    isRulesTips: Boolean,
    onCloseClick: () -> Unit
) {
    val popupHeight = 99.3.dp
    val tobPadding = if (isRulesTips) 16.dp else 0.dp
    val bottomPadding = if (!isRulesTips) 16.dp else 0.dp

    val startPadding = if (isRulesTips) 31.dp else 17.dp
    val endPadding = if (isRulesTips) 14.dp else 28.dp

//    val alphaVal = if (isRulesTips) 0f else 1f

//    Popup(alignment = Alignment.Center) {
    Box(
        modifier = Modifier
            .padding(end = endPadding, start = startPadding)
            .fillMaxWidth()
            .height(popupHeight)
    ) {

        AnimatedVisibility(
            visible = isRulesTips, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 10.dp)
                .size(20.dp)
        ) {
            Image(
                painterResource(id = R.drawable.ic_traingle),
                contentDescription = ""
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .padding(top = tobPadding, bottom = bottomPadding)
                .gradientBackground(
                    listOf(
                        blue500,
                        blue500
                    ), angle = 360f,
                    CornerRadius(30f, 30f)
                )
                .fillMaxSize(),
        ) {
            val (img, titleText, rulesText, closeImg) = createRefs()

            Image(
                painterResource(id = R.drawable.ic_idea),
                modifier = Modifier
                    .constrainAs(img) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .padding(top = 5.dp, start = 12.dp)
                    .width(31.dp)
                    .height(30.dp), contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.posting_tip),
                color = darkGrey,
                fontSize = 14.sp,
                letterSpacing = TextUnit(-0.49f, TextUnitType.Sp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratBold)
                ),
                modifier = Modifier
                    .constrainAs(titleText) {
                        start.linkTo(img.end)
                        top.linkTo(parent.top)
                    }
                    .padding(start = 7.9.dp, top = 13.dp)
            )
            CloseImage(img = R.drawable.ic_clear_dark, modifier = Modifier
                .constrainAs(closeImg) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .padding(end = 12.4.dp, top = 10.dp)) {
                onCloseClick.invoke()
            }
            Text(
                text = stringResource(id = if (isRulesTips) R.string.post_rules_message else R.string.tagged_rules_message),
                color = darkGrey,
                fontSize = 14.sp,
                letterSpacing = TextUnit(-0.49f, TextUnitType.Sp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratRegular)
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .constrainAs(rulesText) {
                        start.linkTo(parent.start)
                        top.linkTo(img.bottom)
                    }
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 9.dp, start = 12.dp, end = 5.1.dp)
            )
        }
        AnimatedVisibility(
            visible = !isRulesTips, modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 10.dp)
                .size(20.dp)
        ) {
            Image(
                painterResource(id = R.drawable.ic_traingle),
                contentDescription = "",
                modifier = Modifier.rotate(180f)
            )
        }
        // }
    }
}