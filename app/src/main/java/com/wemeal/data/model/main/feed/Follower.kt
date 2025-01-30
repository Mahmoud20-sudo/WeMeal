package com.wemeal.data.model.main.feed

import com.wemeal.data.model.user.Birthday
import com.wemeal.data.model.user.UserSettings

data class Follower(
    val _id: String,
    val birthday: Birthday,
    val deliveryAddresses: List<Any>,
    val email: String,
    val facebookProfileId: String,
    val facebookProfilePicUrl: String,
    val firstName: String,
    val interestsCategories: List<Any>,
    val isDoneOnboarding: Boolean,
    val isEmailVerified: Boolean,
    val isFacebookAuthorized: Boolean,
    val isMobileVerified: Boolean,
    val isShowGetStarted: Boolean,
    val isWhatsappAuthorized: Boolean,
    val lastName: String,
    val previousProfilePicturesUrls: List<Any>,
    val profileGalleryUrls: List<Any>,
    val profilePictureUrl: String,
    val role: String,
    val status: String,
    val totalFollowingsCount: Int,
    val userFollowingsCount: Int,
    val userSettings: UserSettings,
    val username: String
)