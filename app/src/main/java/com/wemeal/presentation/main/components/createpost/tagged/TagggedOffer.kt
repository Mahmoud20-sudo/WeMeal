package com.wemeal.presentation.main.components.createpost.tagged

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.wemeal.R
import com.wemeal.data.model.main.tagged.offers.Result
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.ClickableCoilImage
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.CoilImage
import com.wemeal.presentation.util.*

@ExperimentalCoilApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewTaggedOffer() {
    TaggedOffer(
        offer = null,
        onCloseClick = {},
        onItemClick = {}
    )
}

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TaggedOffer(
    offer: Result?,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isSearch: Boolean = false,
    isShared: Boolean = false,
    isDetails: Boolean = false,
    onItemClick: () -> Unit = {},
    onCloseClick: (id: String) -> Unit = {}
) {
    val context = LocalContext.current
    ///////////////////////this will be changed later when integrated with backend===============================
    ConstraintLayout(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    onItemClick()
                })
    ) {
        val (categoryImg, nameText, categoryText, closeImage, divider, viewMenuBox, messageText) = createRefs()
        CoilImage(
            painter = rememberImagePainter(data = offer?.pictureUrl),
            placeholder = R.drawable.ic_cover_placeholder,
            contentDescription = ImageActions.VIEW.contentDescription,
            contentScale = ContentScale.Crop,//later will be changed to tagged restaurant
            alignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(if (!isSearch) CircleShape else RoundedCornerShape(0.dp))
                // clip to the circle shape
                .gradientBackground(
                    listOf(
                        regularBlue,
                        lightPurple
                    ), angle = 360f,
                    CornerRadius(60f, 60f),
                    alpha = 0.1f
                )
                .constrainAs(categoryImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(text = offer?.title?.en ?: "", color = black300, modifier = Modifier
            .constrainAs(nameText) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .padding(start = 48.dp, end = 15.dp),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = FontFamily(montserratSemiBold)
            )
        )
        Text(
            text = offer?.place?.name?.en ?: offer?.place?.name?.en ?: "",
            maxLines = 1,
            color = black300,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular)
            ),
            modifier = Modifier
                .constrainAs(categoryText) {
                    top.linkTo(nameText.bottom)
                    start.linkTo(categoryImg.end)
                }
                .padding(top = 3.dp, start = 8.dp, end = 40.dp, bottom = 10.dp)
        )

        AnimatedVisibility(visible = isSelected, modifier = Modifier
            .constrainAs(closeImage) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }) {
            ClickableImage(
                drawableId = R.drawable.ic_clear_dark,
                imageActions = ImageActions.CLOSE,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .padding(3.dp)
            ) {
                onCloseClick.invoke(offer?.id ?: "")
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(messageText) {
                    top.linkTo(categoryText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, visible = offer?.nonOrderableReason?.isNotEmpty() == true && isDetails
        ) {
            MessageBox(offer?.nonOrderableReason, 32.dp)
        }
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            regularBlue,
                            lightPurple
                        )
                    ),
                    shape = RoundedCornerShape(20.dp),
                    alpha = 0.3f
                )
                .clip(RoundedCornerShape(20.dp))
                .padding(top = 6.dp, start = 16.dp, end = 16.dp, bottom = 6.dp)
                .constrainAs(viewMenuBox) {
                    top.linkTo(categoryText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable(
                    enabled = true,
                    onClickLabel = "VIEW",
                    onClick = {

                    }
                ), visible = offer?.orderable == true
        ) {
            Text(
                text = stringResource(id = R.string.view_menu),
                maxLines = 1,
                color = purple600,
                fontSize = 14.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratMedium)
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        AnimatedVisibility(visible = !isShared, modifier = Modifier.constrainAs(divider) {
            top.linkTo(if (offer?.orderable == true) viewMenuBox.bottom else if (isDetails && offer?.nonOrderableReason?.isNotEmpty() == true) messageText.bottom else categoryImg.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Spacer(
                modifier = Modifier
                    .padding(top = 7.5.dp)
                    .fillMaxWidth()
                    .background(grey.copy(alpha = 0.3f))
                    .height(1.dp)

            )
        }
    }
}