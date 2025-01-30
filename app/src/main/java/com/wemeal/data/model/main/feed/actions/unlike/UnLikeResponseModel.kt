package com.wemeal.data.model.main.feed.actions.unlike

import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.BaseModel

data class UnLikeResponseModel(
    @SerializedName("result")
    val result: com.wemeal.data.model.main.feed.Result?
) : BaseModel()