package com.wemeal.data.model

import androidx.compose.runtime.MutableState


data class RestaurantModel(
    var id: Int,
    var name: String,
    var img: Int,
    var cover: Int,
    var followersCount: Float,
    var isFollowed: MutableState<Boolean>,
    var isOneFollowedUser: Boolean = true,
    var isManyUser: Boolean = false,
    var otherFoodiesCount: Int = -1,
    var isTypeTwoFeed: Boolean = false,
    var followedUserName: String = "",
    var followedUserImg: Int = -1,
) {
    override fun toString(): String {
        return "RestaurantModel(id='$id', name='$name')"
    }
}
