package com.wemeal.presentation.main.components.typefour

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.wemeal.R
import com.wemeal.data.model.*
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.onboarding.components.foody.FoodyItem
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewRecommendedItem() {
    RecommendedItem(
        foodyModel =FoodyModel(
            id = -6, firstName = "Chicken",
            lastName = "Chester",
            img = R.mipmap.ic_chicken_img,
            cover = R.mipmap.atoms_image_image_enabled,
            followersCount = 1.14f,
            postsCount = 100,
            isVerified = false,
            isTypeFourFeed = true,
            isRestaurantActivity = true,
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
            date = "2021-10-26 12:30:00",
            isFollowed = mutableStateOf(false),
            category = Category(
                99,
                30,
                R.drawable.ic_dinner,
                "Coleslaw",
                "Chicken Chester",
                false,
                messageText = "Chicken Chester is closed now"
            ),
            commentsCount = mutableStateOf(112),
            comments = listOf(
                Comment(
                    1,
                    "Haidy Nagy",
                    R.mipmap.avatar_copy_3,
                    "Lorem ipsum "
                )
            )
        )
    )
}


@OptIn(ExperimentalUnitApi::class)
@Composable
fun RecommendedItem(
//    itemIndex: Int = -1,
    foodyModel: FoodyModel,
    onSeeAllClick: () -> Unit = {},
) {

    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (titleText, seeAllText, lazyRow) = createRefs()
        Text(
            text = context.getString(
                if (foodyModel.isRestaurantActivity)
                    R.string.follow_more_rest
                else R.string.foodies_knows
            ),
            color = black300,
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontFamily = FontFamily(montserratMedium),
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(top = 16.dp, start = 16.dp)
        )

        ClickableText(
            modifier = Modifier
                .constrainAs(seeAllText) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(top = 18.dp, end = 16.dp),
            text = stringResource(id = R.string.see_all),
            color = purple600,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = FontFamily(montserratSemiBold)
            )
        ) {
            onSeeAllClick()
        }

        LazyRow(
            modifier = Modifier
                .constrainAs(lazyRow) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                }
                .padding(top = 17.dp)
                .fillMaxWidth()
        ) {
//            if (foodyModel.isRestaurantActivity)
//                items(resturantsList.size) { index ->
//                    RestaurantItem(
//                        restaurantModel = resturantsList[index],
//                        modifier = Modifier
////                            .setGestureModifier(
////                                context,
////                                CustomEvent.USER_SWIPE_FOLLOWED_RESTAURANTS_CAROUSEL, foodyModel
////                            )
//                            .width(179.dp)
//                            .height(244.dp)
//                            .padding(start = 5.dp, end = 6.dp, bottom = 15.dp)
//                    ) { isFollowed ->
//                        context.logMultiEvents(
//                            if (isFollowed) CustomEvent.USER_FOLLOW_RESTAURANT_FROM_CARD else CustomEvent.USER_UNFOLLOW_RESTAURANT_FROM_CARD,
//                            foodyModel = foodyModel
//                        )
//                    }
//                }
//            else
//                items(followedFoodiesList.size) { index ->
//                    FoodyItem(
//                        foodyModel = followedFoodiesList[index],
//                        modifier = Modifier
////                            .setGestureModifier(
////                                context,
////                                CustomEvent.USER_SWIPE_FOLLOWED_FOODIES_CAROUSEL,//
////                                foodyModel
////                            )
//                            .width(179.dp)
//                            .padding(start = 5.dp, end = 6.dp, bottom = 15.dp)
//                    ) { isFollowed ->
//                        context.logMultiEvents(
//                            if (isFollowed) CustomEvent.USER_FOLLOW_FOODIE_FROM_CARD else CustomEvent.USER_UNFOLLOW_FOODIE_FROM_CARD,
//                            foodyModel = foodyModel
//                        )
//                    }
//                }
        }
    }
}

