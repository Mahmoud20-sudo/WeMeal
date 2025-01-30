package com.wemeal.presentation.main.components.posts

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.data.model.Category
import com.wemeal.data.model.FoodyModel
import com.wemeal.data.model.Img
import com.wemeal.presentation.extensions.logMultipleEvents
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewTaggedObject() {
    TaggedObject(
        FoodyModel(
            id = 3,
            firstName = "Haidy",
            lastName = "Nagy",
            img = R.mipmap.avatar_copy_3,
            cover = R.mipmap.ic_cockdoor_cover,
            followersCount = 1.0f,
            postsCount = 200,
            isVerified = false,
            isFollowed = mutableStateOf(false),
            isTypeTwoFeed = true,
            isManyUser = false,
            isOneFollowedUser = true,
            otherFoodiesCount = 5,
            foodyActivityType = ORDERING_ACTIVITY,
            followedUserImg = R.mipmap.ic_chicken_img,
            followedUserName = "Chicken Chester",
            category = Category(
                1,
                33,
                R.mipmap.ic_chicken_img,
                "Fries",
                "Chicken Chester",
                true
            )
        ),
        isDetails = false,
        onObjectClick = {},
        onProfileClick = {}
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TaggedObject(
    foodyModel: FoodyModel,
    isDetails: Boolean,
    isShared: Boolean = false,
    onObjectClick: (categoryId: Int) -> Unit,
    onProfileClick: (restaurantId: Int) -> Unit
) {
    val category = foodyModel.category!!
    val context = LocalContext.current

    val buttonText = when (category._id) {
        1 -> R.string.view_order
        2, 99 -> R.string.view_meal
        98 -> R.string.view_menu
        else -> R.string.view_offer
    }

    ///////////////////////this will be changed later when integrated with backend===============================
    val viewClickEventName =
        if (foodyModel.isTypeTwoFeed)
            when (category._id) {//will be changed later
                1 -> CustomEvent.USER_CLICK_VIEW_ORDER_FROM_ACTIVITY
                2 -> CustomEvent.USER_CLICK_VIEW_MEAL_FROM_MEAL_CARD_FROM_ACTIVITY
                3 -> CustomEvent.USER_CLICK_VIEW_MENU_FROM_RESTAURANT_TAGGED_POST
                4 -> CustomEvent.USER_CLICK_VIEW_MENU_FROM_OFFER_CARD_FROM_ACTIVITY
                4 -> CustomEvent.USER_CLICK_VIEW_MENU_FROM_RESTAURANT_CARD_FROM_ACTIVITY
                else -> CustomEvent.USER_CLICK_VIEW_OFFER_FROM_OFFER_CARD_FROM_ACTIVITY
            }
        else
            when (category._id) {//will be changed later
                1 -> CustomEvent.USER_CLICK_VIEW_ORDER_FROM_TAGGED_ORDER_POST
                2 -> CustomEvent.USER_CLICK_VIEW_MENU_FROM_RESTAURANT_TAGGED_POST
                3 -> CustomEvent.USER_CLICK_VIEW_MENU_FROM_RESTAURANT_TAGGED_POST
                4 -> CustomEvent.USER_CLICK_VIEW_MENU_FROM_OFFER_TAGGED_POST
                else -> CustomEvent.USER_CLICK_VIEW_OFFER_FROM_OFFER_TAGGED_POST
            }

    val profileClickEventName =
        when {
            category._id == 1 -> CustomEvent.USER_CLICK_RESTAURANT_LOGO_FROM_TAGGED_OBJECT //restaurant
            category._id == 2 -> CustomEvent.USER_CLICK_RESTAURANT_ICON_FROM_TAGGED_RESTAURANT // tagged object is the same restaurnat
            category._id % 2 == 0 -> CustomEvent.USER_CLICK_OFFER_ICON_FROM_TAGGED_OFFER
            else -> CustomEvent.USER_CLICK_MEAL_ICON_FROM_TAGGED_MEAL
        }

    val nameClickEventName =
        when (category._id) {
            1 -> CustomEvent.USER_CLICK_ORDER_NAME_FROM_TAGGED_ORDER
            2 -> CustomEvent.USER_CLICK_RESTAURANT_NAME_FROM_TAGGED_RESTAURANT
            3 -> CustomEvent.USER_CLICK_MEAL_NAME_FROM_TAGGED_MEAL
            else -> CustomEvent.USER_CLICK_OFFER_NAME_FROM_TAGGED_OFFER
        }
    ///////////////////////this will be changed later when integrated with backend===============================

    AnimatedVisibility(
        modifier = Modifier
            .padding(end = 16.dp, start = 16.dp),
        visible = category.deleted && isDetails
    ) {
        MessageBox(category.messageText, 44.dp, true)
    }
    AnimatedVisibility(visible = !category.deleted) {
        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 0.dp, bottom = 7.5.dp, end = 16.dp, start = 16.dp)
        ) {
            val (categoryImg, nameText, categoryText, viewMenuBox, messageText, divider) = createRefs()
            ClickableImage(drawableId = category.drawable,
                contentScale = if (category._id == 99 || category._id == 98 || category._id == 97) ContentScale.Inside else ContentScale.Crop,//later will be changed to tagged restaurant
                alignment = Alignment.Center,
                imageActions = ImageActions.VIEW,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
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
                    }) {
                context.logMultipleEvents(profileClickEventName, foodyModel)
                onProfileClick(category.resturantId)
            }
            ClickableText(text = category.name, color = black300, modifier = Modifier
                .constrainAs(nameText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 48.dp),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                style =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(montserratSemiBold)
                )) {
                context.logMultipleEvents(nameClickEventName, foodyModel)
                onObjectClick(category._id)
            }
            Text(
                text = category.category,
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
            AnimatedVisibility(
                modifier = Modifier
                    .constrainAs(messageText) {
                        top.linkTo(categoryText.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, visible = category.messageText.isNotEmpty() && isDetails
            ) {
                MessageBox(category.messageText, 32.dp)
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
                            onObjectClick(category._id)
                            context.logMultipleEvents(viewClickEventName, foodyModel)
                        }
                    ), visible = category.orderable
            ) {
                Text(
                    text = stringResource(id = buttonText),
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
                top.linkTo(if (category.orderable) viewMenuBox.bottom else if (isDetails && category.messageText.isNotEmpty()) messageText.bottom else categoryImg.bottom)
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
}

@ExperimentalAnimationApi
@Composable
private fun MessageBox(message: String, height: Dp, showDivider: Boolean = false) {
    Column {
        Box(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 11.5.dp)
                .background(color = purple800.copy(alpha = 0.15f))
                .fillMaxWidth()
                .height(height = height),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                maxLines = 1,
                color = purple600,
                fontSize = 12.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratMedium)
                )
            )
        }
        AnimatedVisibility(visible = showDivider) {
            Spacer(
                modifier = Modifier
                    .padding(top = 2.5.dp, bottom = 8.5.dp)
                    .fillMaxWidth()
                    .background(grey.copy(alpha = 0.3f))
                    .height(1.dp)

            )
        }
    }

}