package com.wemeal.data.model.main.tagged.products


import com.google.gson.annotations.SerializedName

data class SearchedProducts(
    @SerializedName("result")
    val result: List<Result>?
)