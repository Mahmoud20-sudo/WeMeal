package com.wemeal.data.model.main.feed.tagged.order

import com.google.gson.annotations.SerializedName

data class By(
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("profilePictureUrl")
    val profilePictureUrl: String?,
    @SerializedName("role")
    val role: String?
)