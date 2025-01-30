package com.wemeal.data.model.main.tagged.brands


import com.google.gson.annotations.SerializedName

data class SearchedBrands(
    @SerializedName("result")
    val result: List<Result>?
)