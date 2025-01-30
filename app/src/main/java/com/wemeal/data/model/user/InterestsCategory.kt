package com.wemeal.data.model.user


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class InterestsCategory(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("hoveredIconUrl")
    val hoveredIconUrl: String?,
    @SerializedName("iconUrl")
    val iconUrl: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("mobileIconBackgroundUrl")
    val mobileIconBackgroundUrl: String?,
    @SerializedName("mobileIconUrl")
    val mobileIconUrl: String?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("productTabName")
    val productTabName: ProductTabName?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)