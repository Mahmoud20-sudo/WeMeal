package com.wemeal.data.model.main.feed.tagged.meal


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel
import com.wemeal.data.model.main.feed.tagged.offer.CreatedBy

data class TaggedMealModel(
    @SerializedName("basePrice")
    val basePrice: Int?,
    @SerializedName("category")
    val category: String?,
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
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("isDeleted")
    val isDeleted: Boolean?,
    @SerializedName("isMainProduct")
    val isMainProduct: Boolean?,
    @SerializedName("lastUpdatedBy")
    val lastUpdatedBy: CreatedBy?,
    @SerializedName("likesCount")
    val likesCount: Int?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("orderable")
    val orderable: Boolean?,
    @SerializedName("ordersCount")
    val ordersCount: Int?,
    @SerializedName("place")
    val place: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("pricing")
    val pricing: String?,
    @SerializedName("sectionGroups")
    val sectionGroups: List<Any>?,
    @SerializedName("sections")
    val sections: List<Any>?,
    @SerializedName("sharesCount")
    val sharesCount: Int?,
    @SerializedName("sortOrder")
    val sortOrder: Int?,
    @SerializedName("uniqueName")
    val uniqueName: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("__v")
    val v: Int?
)