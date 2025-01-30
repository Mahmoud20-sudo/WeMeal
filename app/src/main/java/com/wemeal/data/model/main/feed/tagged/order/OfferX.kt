package com.wemeal.data.model.main.feed.tagged.order

import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class OfferX(
    @SerializedName("basePrice")
    val basePrice: Int?,
    @SerializedName("description")
    val description: NameModel?,
    @SerializedName("discountRatio")
    val discountRatio: Double?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("pictureUrl")
    val pictureUrl: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("pricing")
    val pricing: String?,
    @SerializedName("title")
    val title: NameModel?,
    @SerializedName("type")
    val type: String?
)
