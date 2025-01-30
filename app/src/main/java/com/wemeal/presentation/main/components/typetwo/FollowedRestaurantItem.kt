package com.wemeal.presentation.main.components.typetwo

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.data.model.*
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.shared.ClickableImage
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.wemeal.presentation.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalUnitApi
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewFollowedRestaurantItem() {
    FollowedRestaurantItem(
        foodyModel = FoodyModel(
            id = -3, firstName = "Amr",
            lastName = "Osama",
            img = R.drawable.ic_temp,
            cover = R.mipmap.atoms_image_image_enabled,
            followersCount = 1.2f,
            postsCount = 200,
            isVerified = false,
            isFollowed = mutableStateOf(false),
            isTypeTwoFeed = true,
            isManyUser = true,
            isOneFollowedUser = false,
            followedUserImg = R.mipmap.ic_chicken_img,
            otherFoodiesCount = 6,
            followedUserName = "Chicken Chester",
            category = Category(
                2,
                33,
                R.mipmap.ic_chicken_img,
                "Chicken Chester",
                "Fried Chicken",
                true
            )
        )
    ) {}
}

@ExperimentalUnitApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FollowedRestaurantItem(
    foodyModel: FoodyModel,
    onProfileClick: () -> Unit = { },
    onOtherFoodiesClick: () -> Unit = { }
) {
    var followState by remember { mutableStateOf(foodyModel.isFollowed.value) }
    val coroutineScope = rememberCoroutineScope()

    val followIcon: Int = if (foodyModel.isFollowed.value) R.drawable.ic_foody_followed
    else R.drawable.ic_foody_follow

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 12.dp
            ),
    ) {
        val (userImage, userNameText, followedImg, followedUserName, coverImg, followUserImg, followersText, followersNumberText, postsText, postsNumberText) = createRefs()
        ClickableImage(
            drawableId = foodyModel.img,
            imageActions = ImageActions.VIEW,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(32.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .constrainAs(userImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop
        ) {
            onProfileClick()
        }
        SetText(
            foodyModel = foodyModel,
            texSize = 12.sp,
            color = black300,
            modifier = Modifier
                .constrainAs(userNameText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .padding(start = 56.dp, end = 16.dp),
            onOtherFoodiesClick = onOtherFoodiesClick,
            onProfileClick = onProfileClick
        )
        Image(painterResource(id = foodyModel.cover),
            contentDescription = "VIEW",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(coverImg) {
                    top.linkTo(userImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(192.dp)
        )
//        Surface(
//            ,
//            shape = CircleShape,
//            elevation = 10.dp
//        ) {
        Image(
            painterResource(foodyModel.followedUserImg),
            contentDescription = "avatar",
            contentScale = ContentScale.Fit,            // crop the image if it's not a square
            modifier = Modifier
                .constrainAs(followUserImg) {
                    top.linkTo(coverImg.bottom)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp, top = 12.dp)
                .size(40.dp)
        )
//        }
        Text(
            text = foodyModel.followedUserName,
            fontSize = 14.sp,
            color = black300,
            maxLines = 1,
            fontFamily = FontFamily(montserratSemiBold),
            letterSpacing = TextUnit(-0.48f, TextUnitType.Sp),
            modifier = Modifier
                .constrainAs(followedUserName) {
                    top.linkTo(coverImg.bottom)
                    start.linkTo(followUserImg.end)
                }
                .padding(start = 10.dp, top = 12.dp)
        )
        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(followedImg) {
                    top.linkTo(coverImg.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 16.dp, top = 12.dp)
                .width(30.dp)
                .height(27.dp), visible = !followState) {
            ClickableImage(
                drawableId = followIcon,
                imageActions = ImageActions.VIEW, modifier = Modifier
            ) {
                foodyModel.isFollowed.value = !foodyModel.isFollowed.value
                coroutineScope.launch {
                    delay(3000)
                    followState = foodyModel.isFollowed.value
                }
            }
        }
        Text(
            modifier = Modifier
                .constrainAs(followersText) {
                    top.linkTo(followedUserName.bottom)
                    start.linkTo(followUserImg.end)
                }
                .padding(top = 5.dp, start = 10.dp),
            text = "Fried Chicken",//Fried Chicken in some cases
            maxLines = 1,
            color = black300,
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular)
            )
        )
    }
}

@Composable
private fun SetText(
    foodyModel: FoodyModel,
    texSize: TextUnit,
    color: Color,
    modifier: Modifier,
    onOtherFoodiesClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    lateinit var annotatedString: AnnotatedString
    val context = LocalContext.current

    val userName = "${foodyModel.firstName} ${foodyModel.lastName}"
    val andText = context.getString(R.string.and)
    val hasFollowedText =
        when {
            foodyModel.isOneFollowedUser -> context.getString(R.string.has_followed)
            else -> context.getString(R.string.have_followed)
        }

    val othersText = "${foodyModel.otherFoodiesCount} ${context.getString(R.string.others)}"
    val allText = if (foodyModel.isOneFollowedUser)
        "$userName $hasFollowedText ${foodyModel.followedUserName}"
    else//Doaa SalahEldin and 3 others has followed Yassmine Gamal
        "$userName $andText $othersText $hasFollowedText ${foodyModel.followedUserName}"

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
                append(userName)
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append(" $hasFollowedText ")
            }

            addStringAnnotation(
                tag = "terms",
                annotation = foodyModel.followedUserName,
                start = allText.length - foodyModel.followedUserName.length,
                end = allText.length
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
//                start = allText.length - followedUserName.length - hasFollowedText.length - othersText.length,
//                end = allText.length - followedUserName.length - hasFollowedText.length + othersText.length
                start = 0 + userName.length + andText.length,
                end = 0 + userName.length + andText.length + othersText.length

            )

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratBold)
                )
            ) {
                append(othersText)
            }

            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = texSize,
                    fontFamily = FontFamily(montserratMedium)
                )
            ) {
                append(" ${context.getString(R.string.has_followed)} ")
            }

            addStringAnnotation(
                tag = "terms",
                annotation = foodyModel.followedUserName,
                start = allText.length - foodyModel.followedUserName.length,
                end = allText.length
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

    ClickableText(
        text = annotatedString,
        modifier = modifier.padding(top = if (foodyModel.isOneFollowedUser) 6.dp else 1.dp),
        maxLines = if (foodyModel.isOneFollowedUser) 1 else 2,
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
                    onOtherFoodiesClick()
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


