package com.wemeal.presentation.main.components.typetwo

import android.annotation.SuppressLint
import android.util.Log
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
import com.wemeal.presentation.intro.*
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
fun PreviewActivityItem() {
    ActivityItem(
        foodyModel = FoodyModel(
            id = -8,
            firstName = "Doaa",
            lastName = "Salaheldin",
            img = R.drawable.ic_temp,
            cover = R.mipmap.atoms_image_image_enabled,
            followersCount = 556f,
            postsCount = 200,
            isVerified = false,
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
            isFollowed = mutableStateOf(false),
            isTypeTwoFeed = false,
            isRestaurantActivity = true,
            restaurantActivityType = 5,
            followedUserImg = R.mipmap.avatar_copy_3,
            otherFoodiesCount = 27,
            followedUserName = "Yassmin Gamal",

            commentsCount = mutableStateOf(112),
            comments = listOf(
                Comment(
                    1,
                    "Mahmoud Mohamed",
                    R.mipmap.avatar_copy_3,
                    "Lorem ipsum ",
                    replies = listOf(Comment(55, "Macdonald's", R.mipmap.ic_mac_img, "WOOW"))
                )
            )
        )
    )
}

//will be used with Foody or resturant
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalAnimationApi
@Composable
fun ActivityItem(
    foodyModel: FoodyModel,
    onProfileClick: (isFromImg: Boolean) -> Unit = { },
    onOtherFoodiesClick: (contentType: Int) -> Unit = { }
) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .padding(
                top = 8.dp
            ),
    ) {
        val (row, divider) = createRefs()
        Row(
            Modifier
                .constrainAs(row) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableImage(
                drawableId = foodyModel.followedUserImg,
                imageActions = ImageActions.BACK,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            ) {
                onProfileClick(true)
            }
            SetText(
                foodyModel = foodyModel,
                texSize = 12.sp,
                color = black300,
                onProfileClick = onProfileClick,
                onOtherFoodiesClick = onOtherFoodiesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
            )
        }
        Spacer(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(row.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .padding(
                    top = 6.8.dp
                )
                .background(grey.copy(alpha = 0.5f))
                .height(0.5.dp)
        )
    }
}

@Composable
private fun SetText(
    foodyModel: FoodyModel,
    texSize: TextUnit,
    color: Color,
    modifier: Modifier,
    onProfileClick: (isFromImg: Boolean) -> Unit = { },
    onOtherFoodiesClick: (contentType: Int) -> Unit = { }
) {

    val contentTyp = if (foodyModel.isRestaurantActivity) SHARED_TYPE else COMMENTED_ON_TYPE
    lateinit var annotatedString: AnnotatedString
    val context = LocalContext.current

    val firstNameText = foodyModel.followedUserName
    val secondNameText = "${foodyModel.firstName} ${foodyModel.lastName}"

    val middleText =
        if (foodyModel.isRestaurantActivity)
            when (foodyModel.restaurantActivityType) {
                1 -> " ${context.getString(R.string.liked)} "
                2 -> " ${context.getString(R.string.commented_on)} "
                3 -> " ${context.getString(R.string.liked)} "
                4 -> " ${context.getString(R.string.replied_to)} "
                5 -> " ${context.getString(R.string.have_shared)} "
                else -> ""
            }
        else
            when (foodyModel.foodyActivityType) {
                1 -> " ${context.getString(R.string.liked)} "
                2 -> " ${context.getString(R.string.liked)} "
                3 -> " ${context.getString(R.string.commented_on)} "
                4 -> " ${context.getString(R.string.replied_to)} "
                8 -> " ${context.getString(R.string.liked)} "
                else -> ""
            }

    val commentUserName =
        if (foodyModel.isRestaurantActivity)
            when (foodyModel.restaurantActivityType) {
                3, 4 -> "Haidy's "
                else -> ""
            }
        else
            when (foodyModel.foodyActivityType) {
                2, 4 -> "Haidy's "
                8 -> "Haidy Nagy's "
                else -> ""
            }

    val secondMiddleText =
        if (foodyModel.isRestaurantActivity)
            when (foodyModel.restaurantActivityType) {
                3, 4 -> "${context.getString(R.string.comment_on)} "
                else -> ""
            }
        else
            when (foodyModel.foodyActivityType) {
                2, 4 -> "${context.getString(R.string.comment_on)} "
                8 -> "${context.getString(R.string.reply)} ${context.getString(R.string.to_her_comment)} "
                else -> ""
            }

    val andText = context.getString(R.string.and)
    val foodiesCount = "${foodyModel.otherFoodiesCount} ${context.getString(R.string.foodies)}"

    val endText = context.getString(R.string.post)

    val allText = if (foodyModel.isOneFollowedUser)
        "$firstNameText $middleText $commentUserName $secondMiddleText $secondNameText $endText"
    else
        "$firstNameText $andText $foodiesCount $middleText $secondNameText $endText"

    when (foodyModel.isOneFollowedUser) {//single activity
        true -> annotatedString = buildAnnotatedString {
            addStringAnnotation(
                tag = firstNameText,
                annotation = firstNameText,
                start = 0,
                firstNameText.length
            )
            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(firstNameText)
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append(middleText)
            }

            addStringAnnotation(
                tag = commentUserName,
                annotation = commentUserName,
                start = firstNameText.length + middleText.length,
                end = firstNameText.length + middleText.length + commentUserName.length
            )

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(commentUserName)
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append(secondMiddleText)
            }

            addStringAnnotation(
                tag = secondNameText,
                annotation = secondNameText,
                start = firstNameText.length + middleText.length + commentUserName.length + secondMiddleText.length,
                end = firstNameText.length + middleText.length + commentUserName.length + secondMiddleText.length + secondNameText.length
            )

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(secondNameText)
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
        }//Yassmine Gamal and 27 foodies have shared Doaa Salaheldin’s Pos
        else -> annotatedString = buildAnnotatedString {
            addStringAnnotation(
                tag = firstNameText,
                annotation = firstNameText,
                start = 0,
                firstNameText.length
            )
            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(firstNameText)
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
                start = firstNameText.length + andText.length,
                end = firstNameText.length + andText.length + foodiesCount.length
            )

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(foodiesCount)
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append(middleText)
            }

            addStringAnnotation(
                tag = secondNameText,
                annotation = secondNameText,
                start = allText.length - endText.length - secondNameText.length,
                end = allText.length - endText.length
            )

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(secondNameText)
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
    }


    androidx.compose.foundation.text.ClickableText(
        text = annotatedString,
        modifier = modifier,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = firstNameText,
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("policy URL", it.item)
                    onProfileClick(false)
                }

            annotatedString.getStringAnnotations(
                tag = "others",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("others URL", it.item)
                    onOtherFoodiesClick(contentTyp)
                }

            annotatedString.getStringAnnotations(
                tag = commentUserName,
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("commentUserName URL", it.item)
                    onProfileClick(false)
                }

            annotatedString.getStringAnnotations(
                tag = secondNameText,
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    Log.d("terms URL", it.item)
                    onProfileClick(false)
                }
        })
}
