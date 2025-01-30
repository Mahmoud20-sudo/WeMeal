package com.wemeal.data.model.main.feed.tagged.order

import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Place(
    @SerializedName("address")
    val address: Address?,
    @SerializedName("branchName")
    val branchName: NameModel?,
    @SerializedName("hotLineNumber")
    val hotLineNumber: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isPlaceBusy")
    val isPlaceBusy: Boolean?,
    @SerializedName("mobileNumber")
    val mobileNumber: String?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("placeProfilePictureUrl")
    val placeProfilePictureUrl: String?,
    @SerializedName("telephoneNumber")
    val telephoneNumber: String?
)
