package com.wemeal.presentation.main.components.createpost.tagged

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
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
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.CoilImage
import com.wemeal.presentation.util.*
import com.wemeal.data.model.main.tagged.orders.Result
import com.wemeal.presentation.extensions.format

@ExperimentalCoilApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewTaggedOrderItem() {
    TaggedOrderItem(
        order = null,
        onCloseClick = {},
        onItemClick = {}
    )
}

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TaggedOrderItem(
    order: Result?,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onItemClick: () -> Unit = {},
    onCloseClick: (id: String) -> Unit = {}
) {
//    val context = LocalContext.current
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    onItemClick()
                })
    ) {
        val (categoryImg, orderIdText, orderDateText, nameText, categoryText, closeImage, divider) = createRefs()
        CoilImage(
            painter = rememberImagePainter(data = order?.imageURL),
            placeholder = R.drawable.ic_cover_placeholder,
            contentDescription = ImageActions.VIEW.contentDescription,
            contentScale = ContentScale.Crop,//later will be changed to tagged restaurant
            alignment = Alignment.Center,
            modifier = Modifier
                .size(52.dp)
//                .clip(CircleShape)
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
        Text(text = order?.transactionID ?: "",
            modifier = Modifier
                .constrainAs(orderIdText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 60.dp),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = black300.copy(alpha = 0.7f),
                fontFamily = FontFamily(montserratRegular)
            )
        )

        Text(text = format("hh:mma dd/MM/yyyy", order?.createdAt) ?: "",
            modifier = Modifier
                .constrainAs(orderDateText) {
                    top.linkTo(parent.top)
                    end.linkTo(if (isSelected) closeImage.start else parent.end)
                }
                .padding(end = if (isSelected) 8.dp else 0.dp),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = black300.copy(alpha = 0.7f),
                fontFamily = FontFamily(montserratRegular)
            )
        )

        Text(text = order?.title?.en ?: "", color = black300, modifier = Modifier
            .constrainAs(nameText) {
                top.linkTo(orderIdText.bottom)
                start.linkTo(parent.start)
            }
            .padding(start = 60.dp, top = 5.dp),
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
            text = order?.place?.name?.en ?: "",
            maxLines = 1,
            color = black300,
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
                .padding(top = 3.dp, start = 8.dp, bottom = 10.dp)
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
                onCloseClick.invoke(order?._id ?: "")
            }
        }

        Spacer(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(categoryImg.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 13.5.dp, bottom = 10.dp)
                .fillMaxWidth()
                .background(grey.copy(alpha = 0.3f))
                .height(1.dp)
        )
    }
}