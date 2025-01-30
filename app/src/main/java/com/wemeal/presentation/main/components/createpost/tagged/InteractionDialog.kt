package com.wemeal.presentation.main.components.createpost.tagged

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratMedium

@SuppressLint("UnrememberedMutableState")
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewInteractionDialog() {
    InteractionDialog(
        showing = mutableStateOf(false),
        onAddImagesClick = {},
        onAddFromGalleryClick = {},
        onPostAnywayClick = {}
    )
}

@ExperimentalUnitApi
@Composable
fun InteractionDialog(
    showing: MutableState<Boolean>,
    postAnyway: Boolean = false,
    onAddImagesClick: () -> Unit,
    onAddFromGalleryClick: () -> Unit,
    onPostAnywayClick: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {
            showing.value = false
        }) {
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(12.dp),
            backgroundColor = lightGrey
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 44.dp, bottom = 31.dp, start = 26.dp, end = 26.dp)
            ) {
                Image(
                    painterResource(id = R.mipmap.ic_images_interact),
                    contentDescription = ImageActions.VIEW.contentDescription,
                    modifier = Modifier
                        .height(231.dp)
                        .width(114.8.dp)
                )

                Text(
                    text = stringResource(id = R.string.engage_title),
                    color = black200,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    letterSpacing = TextUnit(-0.01f, TextUnitType.Sp),
                    style = TextStyle(
                        fontFamily = FontFamily(montserratMedium)
                    ),
                    modifier = Modifier
                        .padding(top = 21.dp, bottom = 55.dp)
                )

                ClickableText(
                    text = stringResource(id = R.string.add_images),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = FontFamily(
                            montserratMedium
                        ),
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .gradientBackground(
                            listOf(
                                regularBlue,
                                lightPurple
                            ), angle = 360f,
                            CornerRadius(60f, 60f),
                            alpha = 1.0f
                        )
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(top = 7.dp) //
                ) {
                    onAddImagesClick.invoke()
                }

                ClickableText(
                    text = stringResource(id = R.string.choose_from_gallery),
                    textAlign = TextAlign.Center,
                    color = purple600,
                    style = TextStyle(
                        fontFamily = FontFamily(
                            montserratMedium
                        ),
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier
                        .padding(top = 22.dp)
                        .gradientBackground(
                            listOf(
                                regularBlue,
                                lightPurple
                            ), angle = 360f,
                            CornerRadius(60f, 60f),
                            alpha = 0.1f
                        )
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(top = 7.dp)//
                ) {
                    onAddFromGalleryClick.invoke()
                }

                AnimatedVisibility(visible = postAnyway) {
                    ClickableText(
                        text = stringResource(id = R.string.post_anyway),
                        textAlign = TextAlign.Center,
                        color = purple300,
                        style = TextStyle(
                            fontFamily = FontFamily(
                                montserratMedium
                            ),
                            fontSize = 12.6.sp,
                            letterSpacing = TextUnit(0.45f, TextUnitType.Sp)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 28.dp)        // clip to the circle shape
                    ) {
                        onPostAnywayClick.invoke()
                    }
                }
            }
        }
    }
}



