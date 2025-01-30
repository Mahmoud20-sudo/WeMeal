package com.wemeal.data.model.onboarding.nearest


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("ar")
    val ar: String?,
    @SerializedName("en")
    val en: String?
)