package com.wemeal.presentation.shared.dialog

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.intro.black500
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewConfirmationDialog() {
    ConfirmationDialog(
        title = "AAAA",
        message = "BBBB",
        positiveButtonTitle = "Report",
        negativeButtonTitle = "Cancel",
        onPositiveClick = {}, onNegativeClick = {}
    )
}

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    positiveButtonTitle: String,
    negativeButtonTitle: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
) {

    Dialog(
        onDismissRequest = { }
    ) {
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(12.dp),
            backgroundColor = lightGrey
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 13.dp, start = 24.dp)
            ) {
                val (titleText, messageText, positiveButton, negativeButton) = createRefs()
                Text(
                    text = title,
                    color = black500,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratMedium)
                    ),
                    modifier = Modifier
                        .constrainAs(titleText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = message,
                    color = grey200,
                    fontSize = 16.sp,
                    lineHeight = TextUnit(24f, TextUnitType.Sp),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratMedium)
                    ),
                    modifier = Modifier
                        .constrainAs(messageText) {
                            top.linkTo(titleText.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(top = 12.dp, end = 27.dp)
                )
                ClickableText(
                    text = positiveButtonTitle,
                    color = purple600,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(montserratSemiBold)
                    ),
                    modifier = Modifier
                        .constrainAs(negativeButton) {
                            top.linkTo(messageText.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(top = 53.dp, end = 22.dp)
                ) {
                    onPositiveClick()
                }
                ClickableText(
                    text = negativeButtonTitle,
                    color = purple600,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(montserratSemiBold)
                    ),
                    modifier = Modifier
                        .constrainAs(positiveButton) {
                            top.linkTo(messageText.bottom)
                            end.linkTo(negativeButton.start)
                        }
                        .padding(top = 53.dp, end = 27.dp)
                ) {
                    onNegativeClick()
                }
            }

        }
    }
}



