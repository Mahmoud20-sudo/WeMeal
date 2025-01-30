package com.wemeal.presentation.main.components.typeone

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.*
import com.wemeal.data.model.main.feed.Activity
import com.wemeal.data.model.main.feed.Result
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.components.createpost.tagged.TaggedBrand
import com.wemeal.presentation.main.components.createpost.tagged.TaggedMeal
import com.wemeal.presentation.main.components.createpost.tagged.TaggedOffer
import com.wemeal.presentation.main.components.createpost.tagged.TaggedOrder
import com.wemeal.presentation.main.components.dialog.MoreActionsDialog
import com.wemeal.presentation.main.components.posts.*
import com.wemeal.presentation.shared.dialog.ConfirmationDialog
import com.wemeal.presentation.shared.*
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview
@Composable
fun PreviewPostItem() {
    PostItem(
        model = null,
        modifier = Modifier, onProfileClick = {}
    )
}

//will be used with Foody or resturant
@ExperimentalCoilApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun PostItem(
    model: Result?,
    modifier: Modifier,
    isDetails: Boolean = false,
    isShared: Boolean = false,
    onProfileClick: () -> Unit = {},
    onLikeClick: (feedModel: Activity?) -> Unit = {},
    onImageClick: (imgIndex: Int, imgs: List<String>) -> Unit = { _: Int, _: List<String> -> },//used sometimes for opening details page for shared post
    onDeleteClick: (isConfirmed: Boolean) -> Unit = {},
    onFollowClick: () -> Unit = {},
    onReportClick: (isConfirmed: Boolean) -> Unit = {},
) {
    val feedModel = model?.activity
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val size = remember { mutableStateOf(feedModel?.getImages()?.size ?: 0) }

    val followButton: Int =
        if (feedModel?.isFollowed?.value == true) R.drawable.ic_foody_followed else R.drawable.ic_foody_follow

    val date = context.getDateText(feedModel?.createdAt.toString())
    val dateText = when {
        feedModel?.taggedOrder != null -> "${context.getString(R.string.verified_buyer)} • $date" //&& feedModel.user.role == USER
        model?.type == FeedType.PROMOTED.name -> context.getString(R.string.promoted)
        else -> date
    }

    val profileImagePainter = rememberImagePainter(
        data = feedModel?.user?.profilePictureUrl ?: "https://www.example.com/image.jpg",
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    )
    val state = profileImagePainter.state

//    val profileImage = remember { profileImagePainter }

    val dividerPadding = if (feedModel?.comments.isNullOrEmpty()) 0.dp else 8.dp

    val followIcon = when (model?.type) {
        FeedType.PROMOTED.name -> {
            if (feedModel?.isFollowed?.value == true) R.drawable.ic_unfollow else R.drawable.ic_follow
        }
        else -> if (feedModel?.isUnFollowed?.value == false) R.drawable.ic_unfollow else R.drawable.ic_follow
    }
    val followTitle = when (model?.type) {
        FeedType.PROMOTED.name -> {
            if (feedModel?.isFollowed?.value == true) context.getString(R.string.un_follow) else context.getString(
                R.string.follow
            )
        }
        else -> if (feedModel?.isUnFollowed?.value == false) context.getString(R.string.un_follow) else context.getString(
            R.string.follow
        )
    }

    val items = //will be filled based on post type
        when {
            model?.type == FeedType.PROMOTED.name -> listOf(
                MoreModel(R.drawable.ic_share_external, context.getString(R.string.share_external)),
                MoreModel(R.drawable.ic_copy_link, context.getString(R.string.copy_link)),
                MoreModel(followIcon, followTitle)
            )
            SharedPreferencesManager.instance.user?.id == model?.activity?.user?.id -> listOf(
                MoreModel(R.drawable.ic_share_external, context.getString(R.string.share_external)),
                MoreModel(R.drawable.ic_copy_link, context.getString(R.string.copy_link)),
                MoreModel(followIcon, followTitle),
                MoreModel(R.drawable.ic_edit, context.getString(R.string.edit)),
                MoreModel(R.drawable.ic_delete, context.getString(R.string.delete)),
                MoreModel(R.drawable.ic_report, context.getString(R.string.report))
            )
            else -> listOf(
                MoreModel(R.drawable.ic_share_external, context.getString(R.string.share_external)),
                MoreModel(R.drawable.ic_copy_link, context.getString(R.string.copy_link)),
                MoreModel(followIcon, followTitle),
                MoreModel(R.drawable.ic_report, context.getString(R.string.report))
            )
        }


    val followButtonState = remember { mutableStateOf(feedModel?.isFollowed?.value) }
    var showDialog by remember { mutableStateOf(false) }
    var actionType by remember { mutableStateOf(ActionType.REPORT) }

    if (feedModel?.isConfirmShowing?.value == true)
        ShowConfirmation(actionType = actionType) { dimissed ->
            feedModel.isConfirmShowing?.value = false
            if (dimissed) return@ShowConfirmation
            when (actionType) {
                ActionType.DELETE -> onDeleteClick.invoke(true)
                else -> onReportClick.invoke(true)
            }
        }

    if (showDialog)
        MoreActionsDialog(
            items = items
        ) { index ->
            showDialog = false
            actionType = if (index == 4) ActionType.DELETE else ActionType.REPORT
            sendAction(context, index, model, onDeleteClick, onReportClick, onFollowClick)
        }

    ConstraintLayout(
        modifier = modifier
            .background(Color.White)
            .padding(
                bottom = if (isShared) 1.2.dp else 8.5.dp
            ),
    ) {
        val (userImage, userNameText, moreImg, promotedFollowImg, dateTimeText, postText, postImage, categoryBox, divider, socialBox, commentBox, seperator) = createRefs()
        //===========================This part shared btw different types========================
        ClickableCoilImage(
            painter = profileImagePainter,
            placeholder = R.drawable.ic_profile_placeholder,
            contentDescription = ImageActions.VIEW.contentDescription,
            modifier = Modifier
                .padding(
                    top = 12.dp, start = 16.dp, bottom = 8.dp
                )
                .size(40.dp)
                .clip(CircleShape)
                .placeholder(
                    visible = state is ImagePainter.State.Loading || state is ImagePainter.State.Empty,
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
        ) {
            //on profile click
            onProfileClick()
            //sendProfileImgClickLog(context, feedModel)
        }
        TextWithDrawable(
            text = "${feedModel?.user?.firstName} ${feedModel?.user?.lastName}",
            texSize = 14,
            textColor = black300,
            resourceId = if (feedModel?.user?.influencer == true) R.drawable.ic_verified else 0,
            drawableSize = 15,
            drawablePadding = 4,
            shape = CircleShape,
            linesNumber = 1,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .constrainAs(userNameText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(
                    start = 64.dp,
                    end = if (followButtonState.value == true) 20.dp else 60.dp,
                    top = 14.dp
                )
        ) {
            //on profile click
            onProfileClick()
            //sendProfileNameClickLog(context, feedModel)
        }
        AnimatedVisibility(visible = true,
            modifier = Modifier
                .padding(top = 12.dp, end = 12.dp)
                .size(if (isShared) 0.dp else 25.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .constrainAs(moreImg) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            ClickableImage(
                drawableId = R.drawable.ic_more,
                imageActions = ImageActions.MORE,
                modifier = Modifier.rotate(if (feedModel?.promotionReason?.isEmpty() == true) -90f else 0f),
                contentScale = ContentScale.Inside
            ) {
                showDialog = true
                //context.logMultipleEvents(CustomEvent.USER_CLICK_MORE_ACTIONS_ON_POST, feedModel)
            }
        }
        AnimatedVisibility(visible = model?.type == FeedType.PROMOTED.name && followButtonState.value == false,
            modifier = Modifier
                .padding(top = 12.dp, end = 12.dp)
                .width(30.dp)
                .height(27.dp)
                .constrainAs(promotedFollowImg) {
                    top.linkTo(parent.top)
                    end.linkTo(if (feedModel?.promotionReason?.isEmpty() == true) moreImg.start else parent.end)
                }
        ) {
            ClickableImage(
                drawableId = followButton,
                imageActions = ImageActions.FOLLOW, modifier = Modifier
            ) {
                feedModel?.isFollowed?.value = !feedModel?.isFollowed?.value!!
                coroutineScope.launch {
                    delay(3000)
                    followButtonState.value = feedModel.isFollowed.value
                }
            }
        }
        ClickableText(
            text = dateText,
            color = black300.copy(alpha = 0.5f),
            modifier = Modifier
                .constrainAs(dateTimeText) {
                    top.linkTo(userNameText.bottom)
                    start.linkTo(userImage.end)
                }
                .padding(start = 8.dp, bottom = 8.dp),
            style =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = FontFamily(montserratRegular)
            )
        ) {
            if (!isDetails) {
                onImageClick(0, listOf())
                loggingPostAction(
                    context = context,
                    model = model,
                    eventName = CustomEvent.USER_CLICK_TIMESTAMP_TO_VIEW_POST_DETAILS_PAGE,
                    eventCase = EventCase.SUCCESS
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                .constrainAs(postText) {
                    top.linkTo(userImage.bottom)
                    start.linkTo(parent.start)
                },
            visible = !feedModel?.body.isNullOrEmpty()
        ) {
            ExpandableTextView(
                modifier = Modifier,
                text = feedModel!!.body,
                mentionsList = feedModel.mentions,
                onCaptionClick = {
                    if (!isDetails) {
                        onImageClick(0, listOf())
                        loggingPostAction(
                            context = context,
                            model = model,
                            eventName = CustomEvent.USER_CLICK_CAPTION_TO_VIEW_POST_DETAILS_PAGE,
                            eventCase = EventCase.SUCCESS
                        )
                    }
                }, onShowClick = { isExpanded ->
                    loggingPostAction(
                        context = context,
                        model = model,
                        eventName = if (isExpanded) CustomEvent.USER_CLICK_SHOW_MORE_POST else CustomEvent.USER_CLICK_SHOW_LESS_POST,
                        eventCase = EventCase.SUCCESS
                    )
                }
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(postImage) {
                    top.linkTo(if (feedModel?.body.isNullOrEmpty()) userImage.bottom else postText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, visible = size.value > 0
        ) {
            PostImage(imgs = feedModel?.getImages()!!, isFeed = true, onImageClick = { i, list ->
                onImageClick(i, list)
                if (!isDetails)
                    loggingPostAction(
                        context = context,
                        model = model,
                        eventName = CustomEvent.USER_CLICK_IMAGE_TO_VIEW_POST_DETAILS_PAGE,
                        eventCase = EventCase.SUCCESS
                    )
            })
        }
        AnimatedVisibility(
            modifier = Modifier.constrainAs(divider) {
                top.linkTo(if (size.value > 0) postImage.bottom else postText.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, visible = !isShared || feedModel?.isTagged() == true
        ) {
            Spacer(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.5.dp)
                    .fillMaxWidth()
                    .background(grey.copy(alpha = 0.2f))
                    .height(1.dp)
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .constrainAs(categoryBox) {
                    top.linkTo(divider.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.let {
                    return@let if (isShared) {
                        it.clickable {
                            onImageClick(0, listOf())//open details page in case of shared post
                        }
                    } else it
                },
            visible = feedModel?.isTagged() == true
        ) {
            when {
                feedModel?.taggedBrand != null -> TaggedBrand(
                    brand = feedModel.taggedBrand,
                    isDetails = isDetails,
                    isShared = isShared
                )
                feedModel?.taggedMeal != null -> TaggedMeal(
                    meal = feedModel.taggedMeal,
                    isDetails = isDetails,
                    isShared = isShared
                )
                feedModel?.taggedOffer != null -> TaggedOffer(
                    offer = feedModel.taggedOffer,
                    isDetails = isDetails,
                    isShared = isShared
                )
                else -> TaggedOrder(
                    order = feedModel?.taggedOrder,
                    isDetails = isDetails,
                    isShared = isShared
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(socialBox) {
                    top.linkTo(if (feedModel?.isTagged() == false) divider.bottom else categoryBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visible = !isShared
        ) {
            SocialBox(feedModel = feedModel, onLikeClick = onLikeClick)
        }
        AnimatedVisibility(visible = !isShared, modifier = Modifier.constrainAs(seperator) {
            top.linkTo(socialBox.bottom)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
        }) {
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
                    .height(if (feedModel?.comments.isNullOrEmpty()) 0.dp else 1.dp)

            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(commentBox) {
                    top.linkTo(seperator.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visible = !feedModel?.comments.isNullOrEmpty() && !isShared
        ) {
            when {
                isDetails -> {
                    Column {
                        feedModel?.comments?.forEach { comment ->
                            CommentsItem(comment = comment)
                        }
                    }
                }
                else -> CommentsItem(comment = feedModel?.comments!![0])
            }
        }
    }

}

fun sendAction(
    context: Context,
    index: Int,
    model: Result?,
    onDeleteClick: (isConfirmed: Boolean) -> Unit,
    onReportClick: (isConfirmed: Boolean) -> Unit,
    onFollowClick: () -> Unit
) {
    when (index) {
        0 -> {
            context.shareExternal(model?.activity?.deepLink ?: "")
            loggingPostAction(
                context = context,
                model = model,
                eventName = CustomEvent.USER_SHARE_POST_EXTERNALLY,
                eventCase = EventCase.SUCCESS
            )
        }
        1 -> {
            context.copyToClipboard(model?.activity?.deepLink ?: "")
            loggingPostAction(
                context = context,
                model = model,
                eventName = CustomEvent.USER_COPY_POST_LINK,
                eventCase = EventCase.SUCCESS
            )
        }
        2 -> {
            onFollowClick.invoke()
        }
        3 ->
            when (SharedPreferencesManager.instance.user?.id) {
                model?.activity?.user?.id -> Log.e("EDIT", "EDIT")
                // EDIT POST
                else -> onReportClick.invoke(false)
            }
        4 -> {
            when (SharedPreferencesManager.instance.user?.id) {
                model?.activity?.user?.id -> onDeleteClick.invoke(false)
                else -> Log.e(LOADING, LOADING)
            }
            //context.logMultipleEvents(CustomEvent.USER_CLICK_DELETE_POST, feedModel)
        }//delete
        5 -> {
            onReportClick.invoke(false)
        }
    }
}

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ShowConfirmation(
    actionType: ActionType,
    callBack: (dismissed: Boolean) -> Unit
) {
    val context = LocalContext.current
    ConfirmationDialog(
        title = context.getString(if (actionType == ActionType.DELETE) R.string.delete_post_title else R.string.report_post_title),
        message = context.getString(if (actionType == ActionType.DELETE) R.string.delete_post_message else R.string.report_post_message),
        positiveButtonTitle = context.getString(if (actionType == ActionType.DELETE) R.string.delete else R.string.report),
        negativeButtonTitle = context.getString(R.string.cancel),
        onPositiveClick = {
            //DELETE || REPORT ITEM
            callBack.invoke(false)
        },
        onNegativeClick = {
            callBack.invoke(true)
        }
    )
}

private fun sendProfileImgClickLog(context: Context, feedModel: Activity) {
    val eventName =
        when (feedModel.user.role) {
            "Restaurant" -> CustomEvent.USER_CLICK_RESTAURANT_LOGO_FROM_POST
            "Foodie" -> CustomEvent.USER_CLICK_FOODIE_PROFILE_PICTURE_FROM_POST
            else -> CustomEvent.USER_CLICK_ON_THEIR_PROFILE_PICTURE
        }
//    context.activity()?.logMultipleEvents(eventName, feedModel)
}

private fun sendProfileNameClickLog(context: Context, feedModel: Activity) {
    val eventName =
        when (feedModel.user.role) {
            "Restaurant" -> CustomEvent.USER_CLICK_RESTAURANT_NAME_FROM_POST
            "Foodie" -> CustomEvent.USER_CLICK_FOODIE_NAME_FROM_POST
            else -> CustomEvent.USER_CLICK_ON_THEIR_NAME
        }
//    context.logMultipleEvents(eventName, feedModel)
}

//fun sendFollowingClickLog(context: Context, feedModel: Activity) {
//    if (feedModel.isUnFollowed?.value == true) {
//        val eventName = when (feedModel.user.role) {
//            "Restaurant" -> CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_MORE_ACTIONS
//            else -> CustomEvent.USER_UNFOLLOW_FOODIE_FROM_MORE_ACTIONS_ON_POST
//        }
////        context.logMultipleEvents(eventName, feedModel)
//    } else {
//        val eventName = when (feedModel.user.role) {
//            "Restaurant" -> CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_MORE_ACTIONS
//            else -> CustomEvent.USER_UNFOLLOW_FOODIE_FROM_MORE_ACTIONS_ON_POST
//        }
////        context.logMultipleEvents(eventName, feedModel, EventCase.CANCEL)
//    }
//}

