package com.wemeal.data.model.main.tagged.products


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Result(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("imageURL")
    val imageURL: String?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("placeBrand")
    val placeBrand: PlaceBrand?,
    @SerializedName("place")
    val place: PlaceBrand?,
    @SerializedName("orderable")
    val orderable: Boolean?,
    @SerializedName("nonOrderableReason")
    val nonOrderableReason: String?
)