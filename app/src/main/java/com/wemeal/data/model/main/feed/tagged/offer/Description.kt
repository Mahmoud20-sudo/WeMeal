package com.wemeal.data.model.main.feed.tagged.offer


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("ar")
    val ar: String?,
    @SerializedName("en")
    val en: String?
)