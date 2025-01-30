package com.wemeal.data.model.main.feed.actions.like


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.BaseModel

data class LikeResponseModel(
    @SerializedName("result")
    val result: com.wemeal.data.model.main.feed.Result?
) : BaseModel()