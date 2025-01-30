package com.wemeal.data.model.onboarding.suggested_foodies


import com.google.gson.annotations.SerializedName

data class FoodiesModel(
    @SerializedName("result")
    val result: List<Result>?
)