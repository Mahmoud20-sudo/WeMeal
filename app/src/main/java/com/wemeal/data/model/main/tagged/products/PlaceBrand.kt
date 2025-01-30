package com.wemeal.data.model.main.tagged.products


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class PlaceBrand(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("placeBrand")
    val placeBrand: String?,
    @SerializedName("name")
    val name: NameModel?
)