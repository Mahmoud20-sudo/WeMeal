package com.wemeal.data.model.user


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Area(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("name")
    val name: NameModel?
)