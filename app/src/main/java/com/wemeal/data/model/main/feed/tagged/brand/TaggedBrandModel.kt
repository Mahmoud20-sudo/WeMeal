package com.wemeal.data.model.main.feed.tagged.brand


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class TaggedBrandModel(
    @SerializedName("brandLogoURL")
    val brandLogoURL: String?,
    @SerializedName("categories")
    val categories: List<Any>?,
    @SerializedName("commentsCount")
    val commentsCount: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("likesCount")
    val likesCount: Int?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("sharesCount")
    val sharesCount: Int?,
    @SerializedName("tagsCount")
    val tagsCount: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("__v")
    val v: Int?
)