package com.wemeal.data.model.onboarding.nearest


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Result(
    @SerializedName("areaOnlyName")
    val areaOnlyName: NameModel?,
    @SerializedName("city")
    val city: City?,
    @SerializedName("country")
    val country: Country?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("distance")
    val distance: Double?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("subAreaOnlyName")
    val subAreaOnlyName: NameModel?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("__v")
    val v: Int?
)