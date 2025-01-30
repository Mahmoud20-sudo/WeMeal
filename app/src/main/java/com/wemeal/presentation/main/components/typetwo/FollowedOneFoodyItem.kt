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
fun PreviewFollowedOneFoodyItem() {
    FollowedOneFoodyItem(
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
fun FollowedOneFoodyItem(
    foodyModel: FoodyModel,
    onProfileClick: () -> Unit = { },
    onOtherFoodiesClick: () -> Unit = { }
) {
    var followButtonState by remember { mutableStateOf(foodyModel.isFollowed.value) }
    val coroutineScope = rememberCoroutineScope()

    val followIcon: Int = if (foodyModel.isFollowed.value) R.drawable.ic_foody_followed else R.drawable.ic_foody_follow

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
                .height(103.dp)
        )
        Surface(
            modifier = Modifier
                .constrainAs(followUserImg) {
                    top.linkTo(coverImg.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 24.dp, top = 78.dp)
                .size(64.dp),
            shape = CircleShape,
            elevation = 10.dp
        ) {
            Image(
                painterResource(foodyModel.followedUserImg),
                contentDescription = "avatar",
                contentScale = ContentScale.Fit,            // crop the image if it's not a square
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 4.dp)
            )
        }
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
                    start.linkTo(parent.start)
                }
                .padding(start = 100.dp, top = 8.dp)
        )
        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(followedImg) {
                    top.linkTo(coverImg.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 24.dp, top = 8.dp)
                .width(30.dp)
                .height(27.dp), visible = !followButtonState) {
            ClickableImage(
                drawableId = followIcon,
                imageActions = ImageActions.VIEW, modifier = Modifier
            ) {
                foodyModel.isFollowed.value = !foodyModel.isFollowed.value
                coroutineScope.launch {
                    delay(3000)
                    followButtonState = foodyModel.isFollowed.value
                }
            }
        }
        Text(
            modifier = Modifier
                .constrainAs(followersNumberText) {
                    top.linkTo(followedUserName.bottom)
                    start.linkTo(followUserImg.end)
                }
                .padding(top = 14.dp, start = 12.dp),
            text = "${foodyModel.followersCount}K",
            maxLines = 1,
            color = purple300,
            letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold)
            )
        )
        Text(
            modifier = Modifier
                .constrainAs(followersText) {
                    top.linkTo(followedUserName.bottom)
                    start.linkTo(followersNumberText.end)
                }
                .padding(top = 16.dp, start = 5.dp),
            text = stringResource(id = R.string.followers),
            maxLines = 1,
            color = black200,
            letterSpacing = TextUnit(0.25f, TextUnitType.Sp),
            lineHeight = TextUnit(16f, TextUnitType.Sp),
            fontSize = 10.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular)
            )
        )
        Text(
            modifier = Modifier
                .constrainAs(postsNumberText) {
                    top.linkTo(followedUserName.bottom)
                    start.linkTo(followersText.end)
                }
                .padding(top = 14.5.dp, start = 9.9.dp),
            text = foodyModel.postsCount.toString(),
            maxLines = 1,
            color = purple300,
            letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold)
            )
        )
        Text(
            modifier = Modifier
                .constrainAs(postsText) {
                    top.linkTo(followedUserName.bottom)
                    start.linkTo(postsNumberText.end)
                }
                .padding(top = 16.5.dp, start = 5.dp),
            text = stringResource(id = R.string.posts),
            maxLines = 1,
            color = black200,
            letterSpacing = TextUnit(0.25f, TextUnitType.Sp),
            lineHeight = TextUnit(16f, TextUnitType.Sp),
            fontSize = 10.sp,
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
    val othersText = "${foodyModel.otherFoodiesCount} ${context.getString(R.string.others)}"
    val andText = context.getString(R.string.and)

    val middleText =
        if (foodyModel.isOneFollowedUser) context.getString(R.string.has_followed) else context.getString(
            R.string.have_followed)

    val allText = when {
        foodyModel.isOneFollowedUser -> "$userName $middleText ${foodyModel.followedUserName}"
        else -> "$userName $andText $othersText $middleText Ali Hamed"
    }

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
                append(" $middleText ")
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
                append(" $middleText ")
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


