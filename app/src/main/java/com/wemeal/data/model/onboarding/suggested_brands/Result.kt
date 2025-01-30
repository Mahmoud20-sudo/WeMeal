package com.wemeal.data.model.onboarding.suggested_brands

import com.wemeal.data.model.NameModel

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("brandLogoURL")
    val brandLogoURL: String?,
    @SerializedName("coverPictureUrl")
    val coverPictureUrl: Any?,
    @SerializedName("followersCount")
    var followersCount: Int?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isFollowing")
    var isFollowing: Boolean?,
    @SerializedName("name")
    val name: NameModel?
){
    fun getFollowingsCount(): String {
        return when {
            followersCount!! > 1000 -> "$followersCount k"
            else -> "$followersCount"
        }
    }

}