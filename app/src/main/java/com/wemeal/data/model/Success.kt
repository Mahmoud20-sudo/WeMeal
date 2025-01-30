package com.wemeal.data.model

import com.google.gson.annotations.SerializedName

data class Success(
    @SerializedName("ar")
    val ar: String?,
    @SerializedName("en")
    val en: String?
)