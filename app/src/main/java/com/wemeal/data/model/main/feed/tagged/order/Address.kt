package com.wemeal.data.model.main.feed.tagged.order

import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Address(
    @SerializedName("area")
    val area: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("firstLine")
    val firstLine: NameModel?,
    @SerializedName("nearestLandmark")
    val nearestLandmark: List<NearestLandmark>?,
    @SerializedName("nearestParking")
    val nearestParking: List<NearestParking>?
)