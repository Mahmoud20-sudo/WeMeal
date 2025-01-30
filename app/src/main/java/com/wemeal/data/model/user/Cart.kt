package com.wemeal.data.model.user


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("area")
    val area: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("offers")
    val offers: List<Any>?,
    @SerializedName("place")
    val place: String?,
    @SerializedName("products")
    val products: List<Any>?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user")
    val user: String?,
    @SerializedName("__v")
    val v: Int?
)