package com.wemeal.data.model.main.create


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.BaseModel

data class CreatePostResponseModel(
    @SerializedName("result")
    val result: com.wemeal.data.model.main.feed.Result?
) : BaseModel()