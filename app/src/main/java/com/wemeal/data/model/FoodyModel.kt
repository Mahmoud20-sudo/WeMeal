package com.wemeal.data.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.wemeal.R
import com.wemeal.presentation.util.ImageScale
import com.wemeal.presentation.util.NONE

data class FoodyModel(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var category: Category? = null,
    var img: Int,
    var cover: Int,
    var followersCount: Float,
    var postsCount: Int,
    var isVerified: Boolean,
    var text: String? = "",
    var date: String = "2021-10-19 12:00:00",
    @Transient var isFollowed: MutableState<Boolean> = mutableStateOf(true),
    @Transient var isDeleted: MutableState<Boolean> = mutableStateOf(false),
    @Transient var isConfirmShowing: MutableState<Boolean> = mutableStateOf(false),
    @Transient var isUnFollowed: MutableState<Boolean> = mutableStateOf(false),
    var isLiked: MutableState<Boolean> = mutableStateOf(false),
    @Transient var likeCount: MutableState<Int> = mutableStateOf(112),
    @Transient var commentsCount: MutableState<Int> = mutableStateOf(0),
    var images: List<Img>? = listOf(),
    var comments: List<Comment>? = listOf(),
    var sharedPostIndex: Int = -1,
    var taggedObjectName: String = "",
    var isOneFollowedUser: Boolean = true,
    var isManyUser: Boolean = false,
    var otherFoodiesCount: Int = -1,
    var isTypeTwoFeed: Boolean = false,
    var isTypeThreeFeed: Boolean = false,
    var isTypeFourFeed: Boolean = false,
    var followedUserName: String = "",
    var followedUserImg: Int = -1,
    var foodyActivityType: Int = NONE, //1 for following, 2 for ordering, 3 for commenting, 4 for replying
    var isRestaurantActivity: Boolean = false,
    var isFoodieActivity: Boolean = false,
    var isFollowingActivity: Boolean = false,
    var promotionReason: String = "This restaurant has been ordered many times",
    var restaurantActivityType: Int = -1, //1 for like post, 2 for comment, 3 for like comment, 4 for reply, 5 for share
    var userType: String = if (id % 2 == 0) "Restaurant" else if (id % 5 == 0) "Foodie" else "Influencer Foodie", //temp
    var postType: String = if (sharedPostIndex > -1) "Shared" else "Original", //temp
    var cardType: String = if (id % 2 == 0) "Meal" else if (id % 5 == 0) "Offer" else "Restaurant",
    var contentType: String = if (id % 2 == 0) "Activity" else if (id % 5 == 0) "Card" else "Post",
    var objectType: String = if (category?._id == 1) "Order" else if (category?._id == 2) "Meal" else "Offer",
) {
    override fun toString(): String {
        return "FoodyModelModel(id='$id', name='$firstName $lastName')"
    }

    fun followedUserName(): String {
        return followedUserName.split(" ")[0]
    }
}

data class Img(var url: String, var type: ImageScale)

data class Comment(
    var _id: Int,
    var userName: String,
    @DrawableRes var drawable: Int = R.mipmap.ic_mac_img,
    var comment: String,
    var replies: List<Comment> = listOf()
)

data class Category(
    var _id: Int,
    var resturantId: Int,
    @DrawableRes var drawable: Int,
    var name: String,
    var category: String,
    var orderable: Boolean = false,
    var deleted: Boolean = false,
    var messageText: String = ""
)

enum class ActionType {
    DELETE,
    REPORT
}