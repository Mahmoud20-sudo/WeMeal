package com.wemeal.data.model.main.feed

import com.wemeal.data.model.BaseModel

data class FeedDetailsModel(
    val after_id: String,
    val result: Result
) : BaseModel()