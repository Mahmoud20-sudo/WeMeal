package com.wemeal.data.model.main.tagged.brands


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Result(
    @SerializedName("brandLogoURL")
    val brandLogoURL: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: NameModel?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("usersFollowing")
    val usersFollowing: List<String>?,
    @SerializedName("__v")
    val v: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("categories")
    val categories: List<Any>?,
    @SerializedName("orderable")
    val orderable: Boolean?,
    @SerializedName("nonOrderableReason")
    val nonOrderableReason: String?
)