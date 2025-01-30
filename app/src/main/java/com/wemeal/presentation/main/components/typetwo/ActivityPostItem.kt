package com.wemeal.presentation.main.components.typetwo

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.*
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.main.components.typeone.PostItem
import com.wemeal.presentation.main.components.typeone.sendAction
import com.wemeal.presentation.main.components.dialog.MoreActionsDialog
import com.wemeal.presentation.util.events.CustomEvent

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewActivityPostItem() {
    ActivityPostItem(
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
        onProfileClick = {}
    )
}

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalAnimationApi
@Composable
fun ActivityPostItem(
    itemIndex: Int = -1,
    isDetailsPage: Boolean = false,
    foodyModel: FoodyModel,
    onPostClick: (index: Int) -> Unit = {},
    onProfileClick: (id : Int) -> Unit,
    onOtherFoodiesClick: (contentType: Int) -> Unit = { },
    onImageClick: (imgIndex: Int, imgs: List<Img>) -> Unit = { _: Int, _: List<Img> -> },//used sometimes for opening details page for shared post
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val followIcon =
        if (!foodyModel.isUnFollowed.value) R.drawable.ic_unfollow else R.drawable.ic_follow
    val followTitle =
        if (!foodyModel.isUnFollowed.value) context.getString(R.string.un_follow) else context.getString(
            R.string.follow
        )

    val items = listOf(
        MoreModel(R.drawable.ic_share_external, context.getString(R.string.share_external)),
        MoreModel(R.drawable.ic_copy_link, context.getString(R.string.copy_link)),
        MoreModel(followIcon, followTitle)
    )

//    if (showDialog)
//        MoreActionsDialog(items = items, foodyModel = foodyModel, onDismiss = { index ->
//            if (index == -1) showDialog = false
//            sendAction(context, index, foodyModel) { }
//        })

    Column {

        //shown in feed only
        AnimatedVisibility(visible = !isDetailsPage) {
            ActivityItem(foodyModel = foodyModel,
                onProfileClick = { isFromImg ->
                    onProfileClick(foodyModel.id)
                    context.logMultipleEvents(
                        when {
                            isFromImg -> if (foodyModel.isRestaurantActivity) CustomEvent.USER_CLICK_RESTAURANT_LOGO_FROM_ACTIVITY else CustomEvent.USER_CLICK_FOODIE_PROFILE_PICTURE_FROM_ACTIVITY
                            else -> if (foodyModel.isRestaurantActivity) CustomEvent.USER_CLICK_RESTAURANT_NAME_FROM_ACTIVITY else CustomEvent.USER_CLICK_FOODIE_NAME_FROM_ACTIVITY
                        }, foodyModel
                                             )
                }, onOtherFoodiesClick =
                { contentType ->
                    onOtherFoodiesClick(
                        contentType
                    )
                })
        }

//        PostItem(foodyModel = foodyModel,
//            modifier = Modifier
//                .fillMaxSize()
//                .clickable(
//                    enabled = true,
//                    onClickLabel = "",
//                    onClick = {
//                        onPostClick(itemIndex)
//                    }
//                ),
//            onProfileClick = { onProfileClick(foodyModel.id) },
//            onImageClick = { i: Int, imgs: List<Img> -> onImageClick(i, imgs) })
    }
}

