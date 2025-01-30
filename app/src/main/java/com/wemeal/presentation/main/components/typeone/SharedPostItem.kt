package com.wemeal.presentation.main.components.typeone

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.*
import com.wemeal.presentation.extensions.getDateText
import com.wemeal.presentation.extensions.logMultipleEvents
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.components.posts.*
import com.wemeal.presentation.main.components.typetwo.ActivityItem
import com.wemeal.presentation.main.components.typetwo.sendAction
import com.wemeal.presentation.main.components.dialog.MoreActionsDialog
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.shared.ExpandableTextView
import com.wemeal.presentation.shared.TextWithDrawable
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.montserratRegular

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewSharedPostItem() {
    SharedPostItem(
        foodyModel = FoodyModel(
            id = 1, firstName = "Mahmoud",
            lastName = "Mohamed",
            img = R.drawable.ic_temp,
            cover = R.mipmap.ic_cockdoor_cover,
            followersCount = 8.2f,
            postsCount = 235,
            isVerified = true,
            text = "HELLO",
            isUnFollowed = mutableStateOf(false)
        ),
        modifier = Modifier, onProfileClick = {}
    )
}

//will be used with Foody or resturant
@ExperimentalPagerApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SharedPostItem(
    itemIndex: Int = -1,
    foodyModel: FoodyModel,
    modifier: Modifier,
    isDetails: Boolean = false,
    onProfileClick: () -> Unit,
    onPostClick: (index: Int) -> Unit = {},
    onDeleteClick: (index: Int) -> Unit = {},
    onOtherFoodiesClick: (contentType: Int) -> Unit = { },
    onReportClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val date = context.getDateText(foodyModel.date)

    val dividerPadding = if (foodyModel.comments.isNullOrEmpty()) 0.dp else 8.dp
    val followIcon =
        if (!foodyModel.isUnFollowed.value) R.drawable.ic_unfollow else R.drawable.ic_follow
    val followTitle =
        if (!foodyModel.isUnFollowed.value) context.getString(R.string.un_follow) else context.getString(
            R.string.follow
        )
    val items =
        listOf(
            MoreModel(R.drawable.ic_share_external, context.getString(R.string.share_external)),
            MoreModel(R.drawable.ic_copy_link, context.getString(R.string.copy_link)),
            MoreModel(followIcon, followTitle),
            MoreModel(R.drawable.ic_edit, context.getString(R.string.edit)),
            MoreModel(R.drawable.ic_delete, context.getString(R.string.delete)),
            MoreModel(R.drawable.ic_report, context.getString(R.string.report)),
        )

    var showDialog by remember { mutableStateOf(false) }
    var actionType by remember { mutableStateOf(ActionType.REPORT) }

//    ShowConfirmation(actionType = actionType, foodyModel = foodyModel) {
//        when (actionType) {
//            ActionType.DELETE -> onDeleteClick(itemIndex - 1)
//            else -> Log.e("AA", (itemIndex - 1).toString())
//        }
//    }

//    if (showDialog)
//        MoreActionsDialog(items = items, foodyModel = foodyModel, onDismiss = { index ->
//            //            showDialog = false
//            if (index == -1) showDialog = false
//            actionType = if (index == 4) ActionType.DELETE else ActionType.REPORT
//            sendAction(context, index, foodyModel) {
//                onReportClick()
//            }
//        })

    ConstraintLayout(
        modifier = modifier
            .background(Color.White)
            .padding(bottom = 8.5.dp)
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    onPostClick(itemIndex)
                }
            )
    ) {
        val (typeTwoActivityBox, userImage, userNameText, moreImg, dateTimeText, postText, postSurface, socialBox, commentBox, seperator) = createRefs()
        AnimatedVisibility(
            modifier = Modifier.constrainAs(typeTwoActivityBox) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            visible = (foodyModel.isRestaurantActivity || foodyModel.isFoodieActivity) && !isDetails
        ) {
            ActivityItem(foodyModel = foodyModel,
                onProfileClick = { onProfileClick() }, onOtherFoodiesClick = { contentType ->
                    onOtherFoodiesClick(
                        contentType
                    )
                })
        }

        ClickableImage(
            drawableId = foodyModel.img,
            imageActions = ImageActions.BACK,
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp)
                .size(40.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .constrainAs(userImage) {
                    top.linkTo(typeTwoActivityBox.bottom)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop
        ) {
            //on profile click
            onProfileClick()
        }
        AnimatedVisibility(visible = foodyModel.taggedObjectName.isNotEmpty(),
            modifier = Modifier
                .constrainAs(userNameText) {
                    top.linkTo(typeTwoActivityBox.bottom)
                    start.linkTo(parent.start)
//                    end.linkTo(moreImg.start)
                }
                .padding(start = 64.dp, end = 30.dp, top = 14.dp)
        ) {
            SharedPostNameBox(
                modifier = Modifier,
                nameOneText = "${foodyModel.firstName} ${foodyModel.lastName}",
                nameTwoText = foodyModel.taggedObjectName,
                font = R.font.montserrat_semibold,
                fontSize = 14.sp,
                firstNameClick = {
                    context.shortToast(it)
                }
            ) {
                context.shortToast(it)
            }
        }
        AnimatedVisibility(visible = foodyModel.taggedObjectName.isEmpty(),
            modifier = Modifier
                .constrainAs(userNameText) {
                    top.linkTo(typeTwoActivityBox.bottom)
                    start.linkTo(userImage.end)
                    end.linkTo(moreImg.start)
                }) {
            TextWithDrawable(
                text = "${foodyModel.firstName} ${foodyModel.lastName}",
                texSize = 14,
                textColor = black300,
                resourceId = if (foodyModel.isVerified) R.drawable.ic_verified else 0,
                drawableSize = 15,
                drawablePadding = 8,
                shape = CircleShape,
                linesNumber = 1,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 54.dp, end = 30.dp, top = 12.dp)
            ) {
                //on profile click
                onProfileClick()
            }
        }
        ClickableImage(
            drawableId = R.drawable.ic_more,
            imageActions = ImageActions.MORE,
            modifier = Modifier
                .padding(end = 12.dp, top = 12.dp)
                .width(25.dp)
                .height(25.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
//                .padding(start = 5.dp , end = 5.dp, top = 5.dp, bottom = 5.dp)
                .constrainAs(moreImg) {
                    top.linkTo(typeTwoActivityBox.bottom)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Inside
        ) {
            showDialog = true
            context.logMultipleEvents(
                CustomEvent.USER_CLICK_MORE_ACTIONS_ON_POST,
                foodyModel = foodyModel
            )
        }
        ClickableText(
            text = date,
            color = black300.copy(alpha = 0.5f),
            modifier = Modifier
                .constrainAs(dateTimeText) {
                    top.linkTo(userNameText.bottom)
                    start.linkTo(userImage.end)
                }
                .padding(start = 8.dp, bottom = 10.dp),
            style =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = FontFamily(montserratRegular)
            )
        ) {
            if (!isDetails) {
                onPostClick(itemIndex)
                context.logMultipleEvents(
                    CustomEvent.USER_CLICK_TIMESTAMP_TO_VIEW_POST_DETAILS_PAGE,
                    foodyModel
                )
            }
        }
        ExpandableTextView(
            modifier = Modifier
                .padding(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                .constrainAs(postText) {
                    top.linkTo(dateTimeText.bottom)
                    start.linkTo(parent.start)
                },
            text = foodyModel.text!!,
            onCaptionClick = {
                if (!isDetails) {
                    onPostClick(itemIndex)
                    context.logMultipleEvents(
                        CustomEvent.USER_CLICK_CAPTION_TO_VIEW_POST_DETAILS_PAGE,
                        foodyModel
                    )
                }
            },
            mentionsList = null
        )
        Surface(
            contentColor = Color.Transparent,
            modifier = Modifier
                .constrainAs(postSurface) {
                    top.linkTo(postText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = 16.dp, end = 16.dp)
                .border(1.dp, lightGray100, shape = RoundedCornerShape(7.dp)),
            elevation = 0.dp,
        ) {
//            PostItem(foodyModel = foodiesList[foodyModel.sharedPostIndex],
//                isShared = true,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clickable(
//                        enabled = true,
//                        onClickLabel = "",
//                        onClick = {
//                            onPostClick(foodyModel.sharedPostIndex + 1)
//                        }
//                    ),
//                onProfileClick = { onPostClick(foodyModel.sharedPostIndex + 1) },
//                onImageClick = { _: Int, _: List<Img> -> onPostClick(foodyModel.sharedPostIndex + 1) })
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .constrainAs(socialBox) {
                    top.linkTo(postSurface.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visible = true
        ) {
//            SocialBox(
//                foodyModel = foodyModel,
//                commentsCount = 568,
//                sharesCount = 15.3f
//            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = dividerPadding,
                    bottom = dividerPadding
                )
                .background(grey.copy(alpha = 0.3f))
                .height(if (foodyModel.comments.isNullOrEmpty()) 0.dp else 1.dp)
                .constrainAs(seperator) {
                    top.linkTo(socialBox.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(commentBox) {
                    top.linkTo(seperator.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visible = !foodyModel.comments.isNullOrEmpty()
        ) {
            when {
                isDetails -> {
                    Column {
                        foodyModel.comments?.forEach { comment ->
                            CommentsItem(comment = comment)
                        }
                    }
                }
                else -> CommentsItem(comment = foodyModel.comments!![0])
            }
        }
    }
}