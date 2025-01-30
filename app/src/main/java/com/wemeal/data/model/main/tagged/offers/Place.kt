package com.wemeal.data.model.main.tagged.offers


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel
import com.wemeal.data.model.main.tagged.products.PlaceBrand

data class Place(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("branchName")
    val branchName: NameModel?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("placeBrand")
    val placeBrand: String?,
    @SerializedName("placeProfileCoverPictureUrl")
    val placeProfileCoverPictureUrl: String?,
    @SerializedName("placeProfilePictureUrl")
    val placeProfilePictureUrl: String?
)