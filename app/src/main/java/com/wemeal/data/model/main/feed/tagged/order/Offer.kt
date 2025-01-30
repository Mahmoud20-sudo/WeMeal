package com.wemeal.data.model.main.feed.tagged.order


import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("offer")
    val offer: OfferX?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("sectionGroups")
    val sectionGroups: List<Any>?,
    @SerializedName("sections")
    val sections: List<Section>?
)