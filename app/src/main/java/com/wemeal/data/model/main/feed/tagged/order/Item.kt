package com.wemeal.data.model.main.feed.tagged.order

import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Item(
    @SerializedName("description")
    val description: NameModel?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("title")
    val title: NameModel?
)