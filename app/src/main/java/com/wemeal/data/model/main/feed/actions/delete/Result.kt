package com.wemeal.data.model.main.feed.actions.delete

import com.wemeal.data.model.BaseModel

data class Result(
    val deletedCount: Int
) : BaseModel()