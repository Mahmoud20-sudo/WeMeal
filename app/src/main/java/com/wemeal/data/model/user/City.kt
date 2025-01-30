package com.wemeal.data.model.user


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class City(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: NameModel?
)