package com.wemeal.data.model.main.feed.tagged.offer


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel
import com.wemeal.data.model.main.feed.tagged.order.Section

data class TaggedOfferModel(
    @SerializedName("basePrice")
    val basePrice: Int?,
    @SerializedName("claimsCount")
    val claimsCount: Int?,
    @SerializedName("clicksCount")
    val clicksCount: Int?,
    @SerializedName("commentsCount")
    val commentsCount: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("createdBy")
    val createdBy: CreatedBy?,
    @SerializedName("deepLink")
    val deepLink: String?,
    @SerializedName("description")
    val description: NameModel?,
    @SerializedName("discountRatio")
    val discountRatio: Float?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("excludedProducts")
    val excludedProducts: List<Any>?,
    @SerializedName("favoritesCount")
    val favoritesCount: Int?,
    @SerializedName("galleryUrls")
    val galleryUrls: List<String>?,
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("lastUpdatedBy")
    val lastUpdatedBy: CreatedBy?,
    @SerializedName("likesCount")
    val likesCount: Int?,
    @SerializedName("offerSponsorships")
    val offerSponsorships: List<Any>?,
    @SerializedName("openingTimes")
    val openingTimes: OpeningTimes?,
    @SerializedName("openingTimesShift")
    val openingTimesShift: OpeningTimesShift?,
    @SerializedName("openingTimesStatus")
    val openingTimesStatus: String?,
    @SerializedName("orderable")
    val orderable: Boolean?,
    @SerializedName("ordersCount")
    val ordersCount: Int?,
    @SerializedName("PMId")
    val pMId: Any?,
    @SerializedName("pictureUrl")
    val pictureUrl: String?,
    @SerializedName("placeId")
    val placeId: String?,
    @SerializedName("placeLocation")
    val placeLocation: PlaceLocation?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("pricing")
    val pricing: String?,
    @SerializedName("sectionGroups")
    val sectionGroups: List<Any>?,
    @SerializedName("sections")
    val sections: List<Section>?,
    @SerializedName("sharesCount")
    val sharesCount: Int?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("timedOfferDetails")
    val timedOfferDetails: TimedOfferDetails?,
    @SerializedName("title")
    val title: Title?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("uniqueName")
    val uniqueName: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("__v")
    val v: Int?,
    @SerializedName("visible")
    val visible: Boolean?
)