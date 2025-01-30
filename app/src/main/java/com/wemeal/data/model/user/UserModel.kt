package com.wemeal.data.model.user

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("birthday")
    val birthday: Birthday?,
//    @SerializedName("cart")
//    val cart: Cart?,
    @SerializedName("deliveryAddresses")
    val deliveryAddresses: List<DeliveryAddresse>?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("facebookProfileId")
    val facebookProfileId: String?,
    @SerializedName("facebookProfilePicUrl")
    val facebookProfilePicUrl: String?,
    @SerializedName("facebookProfileUrl")
    val facebookProfileUrl: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("interestsCategories")
    val interestsCategories: List<InterestsCategory>?,
    @SerializedName("isEmailVerified")
    val isEmailVerified: Boolean?,
    @SerializedName("isFacebookAuthorized")
    val isFacebookAuthorized: Boolean?,
    @SerializedName("isMobileVerified")
    val isMobileVerified: Boolean?,
    @SerializedName("isShowGetStarted")
    val isShowGetStarted: Boolean?,
    @SerializedName("isWhatsappAuthorized")
    val isWhatsappAuthorized: Boolean?,
    @SerializedName("isDoneOnboarding")
    var isDoneOnboarding: Boolean? = false,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("pmFollowers")
    val pmFollowers: List<Any>?,
    @SerializedName("pmFollowings")
    val pmFollowings: List<Any>?,
    @SerializedName("pmFollowingsCount")
    val pmFollowingsCount: Int?,
    @SerializedName("previousProfilePicturesUrls")
    val previousProfilePicturesUrls: List<Any>?,
    @SerializedName("profileGalleryUrls")
    val profileGalleryUrls: List<Any>?,
    @SerializedName("profilePictureUrl")
    val profilePictureUrl: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalFollowingsCount")
    val totalFollowingsCount: Int?,
    @SerializedName("userFollowers")
    val userFollowers: List<Any>?,
    @SerializedName("userFollowings")
    val userFollowings: List<String>?,
    @SerializedName("userFollowingsCount")
    val userFollowingsCount: Int?,
    @SerializedName("userSettings")
    val userSettings: UserSettings?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("numPosts")
    var numPosts: Int?,
    @SerializedName("numFollowings")
    val numFollowings: Int?,
    @SerializedName("numFollowers")
    val numFollowers: Int?,
    @SerializedName("influencer")
    val influencer: Boolean,
)
