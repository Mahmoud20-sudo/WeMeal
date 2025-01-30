package com.wemeal.presentation.main.components.typetwo

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.*
import com.wemeal.presentation.extensions.copyToClipboard
import com.wemeal.presentation.extensions.shareExternal
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.components.posts.CommentsItem
import com.wemeal.presentation.main.components.posts.PostImage
import com.wemeal.presentation.main.components.posts.SocialBox
import com.wemeal.presentation.main.components.posts.TaggedObject
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.*

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewTaggedObjectPostItem() {
    TaggedObjectPostItem(
        foodyModel = FoodyModel(
            id = 1, firstName = "Mahmoud",
            lastName = "Mohamed",
            img = R.drawable.ic_temp,
            cover = R.mipmap.ic_cockdoor_cover,
            followersCount = 8.2f,
            postsCount = 235,
            isVerified = true,
            text = "HELLO",
            isUnFollowed = mutableStateOf(false),
            category = Category(
                4,
                30,
                R.mipmap.ic_dahan_cover,
                "Buy 2 Grilled Chicken Meal and get one Grilled Shawerma Sandwich",
                "Dahan",
                true
            )
        )
    )
}

//will be used with Tagged restaurant activity and also with foodie made an order activity
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalAnimationApi
@Composable
fun TaggedObjectPostItem(
    foodyModel: FoodyModel,
    isDetails: Boolean = false,
    onProfileClick: () -> Unit = { },
    onImageClick: (imgIndex: Int, imgs: List<Img>) -> Unit = { i: Int, list: List<Img> -> },
    onOtherFoodiesClick: (contentType: Int) -> Unit = { }//used sometimes for opening details page for shared post
) {
//    ConstraintLayout(
//        modifier = Modifier
//            .background(Color.White)
//            .padding(
//                top = 12.dp,
//                bottom = 3.5.dp
//            ),
//    ) {
//        val (topRow, postImage, taggedObjectBox, socialBox, commentBox, seperator) = createRefs()
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .constrainAs(topRow) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                }
//                .fillMaxWidth()) {
//            ClickableImage(
//                drawableId = foodyModel.img,
//                imageActions = ImageActions.BACK,
//                modifier = Modifier
//                    .padding(start = 16.dp)
//                    .size(32.dp)
//                    .clip(CircleShape)                       // clip to the circle shape
//                ,
//                contentScale = ContentScale.Crop
//            ) {
//                onProfileClick()
//            }
//            SetText(
//                foodyModel = foodyModel,
//                texSize = 12.sp,
//                color = black300,
//                onProfileClick = onProfileClick,
//                onOtherFoodiesClick = onOtherFoodiesClick,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 8.dp, end = 16.dp, top = 0.7.dp),
//            )
//        }
//        AnimatedVisibility(
//            modifier = Modifier
//                .constrainAs(postImage) {
//                    top.linkTo(topRow.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .padding(top = 12.dp),
//            visible = foodyModel.foodyActivityType == ORDERING_ACTIVITY //ordered from
//        ) {
////            PostImage(imgs = foodyModel.images!!, onImageClick = onImageClick)
//        }
//        AnimatedVisibility(
//            modifier = Modifier
//                .constrainAs(postImage) {
//                    top.linkTo(topRow.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .padding(top = 12.dp),
//            visible = foodyModel.foodyActivityType != ORDERING_ACTIVITY //posted about
//        ) {
//            ClickableImage(
//                foodyModel.cover,
//                imageActions = ImageActions.VIEW,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(192.dp),
//                contentScale = ContentScale.Crop,
//                alignment = androidx.compose.ui.Alignment.Center
//            ) {
//                onImageClick(0, listOf(Img(foodyModel.cover, ImageScale.RECTANGULAR)))
//            }
//        }
//        AnimatedVisibility(
//            modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(taggedObjectBox) {
//                    top.linkTo(postImage.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
////                .let {
////                    return@let it.clickable {
////                        //onImageClick()
////                    }
////                }
//                .padding(top = 12.dp),
//            visible = true
//        ) {
//            TaggedObject(foodyModel,
//                isDetails,
//                isShared = foodyModel.foodyActivityType == ORDERING_ACTIVITY,
//                onProfileClick = {
//                    onProfileClick()
//                },
//                onObjectClick = { onProfileClick() })
//        }
//        AnimatedVisibility(
//            modifier = Modifier
//                .padding(start = 16.dp, end = 16.dp)
//                .constrainAs(socialBox) {
//                    top.linkTo(taggedObjectBox.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                },
//            visible = foodyModel.foodyActivityType != ORDERING_ACTIVITY
//        ) {
////            SocialBox(
////                foodyModel = foodyModel,
////                commentsCount = 568,
////                sharesCount = 17.2f
////            )
//        }
//        AnimatedVisibility(
//            modifier = Modifier
//                .constrainAs(seperator) {
//                    top.linkTo(socialBox.bottom)
//                    end.linkTo(parent.end)
//                    start.linkTo(parent.start)
//                }
//                .fillMaxWidth()
//                .padding(
//                    start = 16.dp,
//                    end = 16.dp,
//                    top = 5.3.dp,
//                    bottom = 5.3.dp
//                ),
//            visible = foodyModel.foodyActivityType != ORDERING_ACTIVITY
//        ) {
//            Spacer(
//                modifier = Modifier
//                    .background(grey.copy(alpha = 0.3f))
//                    .height(if (foodyModel.comments.isNullOrEmpty()) 0.dp else 1.dp)
//            )
//        }
//        AnimatedVisibility(
//            modifier = Modifier
//                .padding(start = 16.dp, end = 16.dp)
//                .constrainAs(commentBox) {
//                    top.linkTo(seperator.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                },
//            visible = !foodyModel.comments.isNullOrEmpty() && foodyModel.foodyActivityType != ORDERING_ACTIVITY
//        ) {
//            when {
//                isDetails -> {
//                    Column {
//                        foodyModel.comments?.forEach { comment ->
//                            CommentsItem(comment = comment)
//                        }
//                    }
//                }
//                else -> CommentsItem(comment = foodyModel.comments!![0])
//            }
//        }
//    }

}

fun sendAction(
    context: Context,
    index: Int,
    foodyModel: FoodyModel,
    onReportClick: () -> Unit
) {
    when (index) {
        0 -> context.shareExternal("https://qurba.app.link/3TsAwxiAPkb")
        1 -> context.copyToClipboard("https://qurba.app.link/3TsAwxiAPkb")
        2 -> foodyModel.isUnFollowed.value = !foodyModel.isUnFollowed.value
        3 -> Log.e("EDIT", "EDIT")// EDIT POST
        4 -> foodyModel.isConfirmShowing.value = true //delete and report
        5 -> onReportClick()
    }
}

@Composable
private fun SetText(
    foodyModel: FoodyModel,
    texSize: TextUnit,
    color: Color,
    modifier: Modifier,
    onProfileClick: () -> Unit = { },
    onOtherFoodiesClick: (contentType: Int) -> Unit = { }
) {

//    Doaa SalahEldin and 23 others posted about Chicken Chester

    lateinit var annotatedString: AnnotatedString
    val context = LocalContext.current

    val userName = "${foodyModel.firstName} ${foodyModel.lastName}"
    val andText = context.getString(R.string.and)
    val middleText =
        when (foodyModel.foodyActivityType) {
            1, 5, 6, 7 -> context.getString(R.string.liked)
            ORDERING_ACTIVITY -> if (foodyModel.isOneFollowedUser) context.getString(R.string.has_ordered) else context.getString(
                R.string.have_ordered
            )
            else -> context.getString(R.string.posted_about)
        }

    val othersText = "${foodyModel.otherFoodiesCount} ${context.getString(R.string.others)}"
    val allText = "$userName $andText $othersText $middleText ${foodyModel.followedUserName}"

    val endText =
        when (foodyModel.foodyActivityType) {
            5 -> context.getString(R.string.offer)
            6 -> context.getString(R.string.meal)
            ORDERING_ACTIVITY, 7 -> "" //RESTAURNAT TYPE  IWLL BE EMPTY
            else -> context.getString(R.string.post)
        }

    //Yassmine Gamal has ordered from Mcdonald's
    if (foodyModel.isOneFollowedUser)
        annotatedString = buildAnnotatedString {
            addStringAnnotation(
                tag = userName,
                annotation = userName,
                start = 0,
                userName.length
            )
            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append("$userName ")
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append("$middleText ")
            }

            addStringAnnotation(
                tag = "terms",
                annotation = foodyModel.followedUserName,
                start = userName.length + middleText.length,
                end = userName.length + middleText.length + foodyModel.followedUserName.length
            )
            withStyle(
                style = SpanStyle(
                    color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(foodyModel.followedUserName)
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append(endText)
            }
        }
    else
        annotatedString = buildAnnotatedString {
            addStringAnnotation(
                tag = userName,
                annotation = userName,
                start = 0,
                userName.length
            )
            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(userName)
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append(" $andText ")
            }

            addStringAnnotation(
                tag = "others",
                annotation = "others",
                start = allText.length - foodyModel.followedUserName.length - middleText.length - othersText.length,
                end = allText.length - foodyModel.followedUserName.length - middleText.length - othersText.length + othersText.length
            )

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append("$othersText ")
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append("$middleText ")
            }

            addStringAnnotation(
                tag = "terms",
                annotation = foodyModel.followedUserName,
                start = allText.length - foodyModel.followedUserName.length,
                end = allText.length - foodyModel.followedUserName.length + foodyModel.followedUserName.length
            )
            withStyle(
                style = SpanStyle(
                    color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(foodyModel.followedUserName)
            }
        }

    androidx.compose.foundation.text.ClickableText(
        text = annotatedString,
        modifier = modifier,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = userName, start = offset, end = offset)
                .firstOrNull()?.let {
                    Log.d("policy URL", it.item)
                    onProfileClick()
                }

            annotatedString.getStringAnnotations(
                tag = "others",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("others URL", it.item)
                    onOtherFoodiesClick(
                        if (foodyModel.foodyActivityType == ORDERING_ACTIVITY) ORDERED_FROM_TYPE else
                            POSTED_ABOUT_TYPE
                    )
                }

            annotatedString.getStringAnnotations(
                tag = "terms",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("terms URL", it.item)
                    onProfileClick()
                }
        })
}
