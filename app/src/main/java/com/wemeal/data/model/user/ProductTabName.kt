package com.wemeal.data.model.user


import com.google.gson.annotations.SerializedName

data class ProductTabName(
    @SerializedName("ar")
    val ar: String?,
    @SerializedName("en")
    val en: String?
)