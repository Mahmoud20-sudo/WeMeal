package com.wemeal.data.model.main.feed.tagged.offer


import com.google.gson.annotations.SerializedName

data class PlaceLocation(
    @SerializedName("coordinates")
    val coordinates: List<Double>?,
    @SerializedName("type")
    val type: String?
)