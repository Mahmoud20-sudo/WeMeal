package com.wemeal.data.model.onboarding.nearest


import com.google.gson.annotations.SerializedName

data class NearestModel(
    @SerializedName("result")
    val result: List<Result>?
)