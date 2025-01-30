package com.wemeal.presentation.main.components.createpost

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.components.createpost.rules.RuleItem
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratBold
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratRegular

@ExperimentalUnitApi
@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun PreviewBottomSheetContent() = BottomSheetContent(true) {}

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun BottomSheetContent(
    isPosting : Boolean,
    onCloseClick: (isAgreed: Boolean) -> Unit
) {
    val context = LocalContext.current
    val items = context.resources.getStringArray(R.array.rules_items)
    val icons = context.resources.getStringArray(R.array.rules_items_icons_hex)

    ConstraintLayout(
        modifier = Modifier
            .padding(all = 22.dp)
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        val (titleText, messageText, spacer, closeImg, lazyColumn, agreeBox) = createRefs()

        Text(
            text = stringResource(id = R.string.community_rules_title),
            color = darkGrey,
            fontSize = 14.sp,
            letterSpacing = TextUnit(-0.49f, TextUnitType.Sp),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratBold)
            ),
            modifier = Modifier
                .constrainAs(titleText) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )
        ClickableImage(
            drawableId = R.drawable.ic_clear_dark,
            imageActions = ImageActions.CLOSE,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .constrainAs(closeImg) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .size(14.dp)
        ) {
            onCloseClick.invoke(false)
        }
        Spacer(modifier = Modifier
            .constrainAs(spacer) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(titleText.bottom)
            }
            .padding(top = 15.5.dp, bottom = 10.5.dp)
            .background(grey)
            .fillMaxWidth()
            .height(0.5.dp)
        )

        Text(
            text = stringResource(id = R.string.community_rules_message),
            color = black300,
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular)
            ),
            modifier = Modifier
                .constrainAs(messageText) {
                    start.linkTo(parent.start)
                    top.linkTo(spacer.bottom)
                }
        )
        Column(
            modifier = Modifier
                .constrainAs(lazyColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(messageText.bottom)
                    bottom.linkTo(agreeBox.top)
                }
                .padding(top = 26.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            for (index in items.indices) {
                RuleItem(itemName = items[index], itemIconHex = icons[index])
            }
        }

        AnimatedVisibility(visible = isPosting,
            modifier = Modifier
                .constrainAs(agreeBox) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .gradientBackground(
                    listOf(
                        regularBlue,
                        lightPurple
                    ), angle = 45f,
                    CornerRadius(60f, 60f),
                    alpha = 0.3f
                )
                .fillMaxWidth()
                .height(32.dp)
                .padding(top = 6.dp)
        ) {
            ClickableText(
                text = stringResource(id = R.string.agreed),
                modifier = Modifier.fillMaxSize(),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = purple600,
                    fontFamily = FontFamily(montserratMedium),
                    textAlign = TextAlign.Center
                )
            ) {
                onCloseClick.invoke(true)
            }
        }
    }
}
