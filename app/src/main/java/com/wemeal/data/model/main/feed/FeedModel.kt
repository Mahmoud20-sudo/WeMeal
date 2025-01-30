package com.wemeal.data.model.main.feed

import com.wemeal.data.model.BaseModel

data class FeedModel(
    val after_id: String,
    val result: MutableList<Result>
) : BaseModel()