package com.wemeal.data.model.onboarding.nearest


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class City(
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("__v")
    val v: Int?
)