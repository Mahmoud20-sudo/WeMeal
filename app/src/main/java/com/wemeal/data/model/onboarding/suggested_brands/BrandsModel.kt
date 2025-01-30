package com.wemeal.data.model.onboarding.suggested_brands

import com.google.gson.annotations.SerializedName

data class BrandsModel(
    @SerializedName("result")
    val result: List<Result>?
)