package com.wemeal.data.model.main


import com.google.gson.annotations.SerializedName

data class SearchUsersModel(
    @SerializedName("result")
    val result: List<Result>?
)