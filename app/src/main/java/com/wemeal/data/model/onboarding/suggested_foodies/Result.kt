package com.wemeal.data.model.onboarding.suggested_foodies


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("coverPictureUrl")
    val coverPictureUrl: Any?,
    @SerializedName("facebookProfilePicUrl")
    val facebookProfilePicUrl: Any?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("followersCount")
    var followersCount: Int?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isFollowing")
    var isFollowing: Boolean?,
    @SerializedName("isVerified")
    val isVerified: Boolean?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("postsCount")
    val postsCount: Int?,
    @SerializedName("profilePictureUrl")
    val profilePictureUrl: String?
) {
    fun getFollowingsCount(): String {
        return when {
            followersCount!! > 1000 -> "$followersCount k"
            else -> "$followersCount"
        }
    }

    fun getPostsCount(): String {
        return when {
            postsCount!! > 1000 -> "$postsCount k"
            else -> "$postsCount"
        }
    }
}