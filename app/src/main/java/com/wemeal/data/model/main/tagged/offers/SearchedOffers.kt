package com.wemeal.data.model.main.tagged.offers


import com.google.gson.annotations.SerializedName

data class SearchedOffers(
    @SerializedName("result")
    val result: List<Result>?
)