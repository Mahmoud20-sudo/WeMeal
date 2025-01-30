package com.wemeal.data.model.main.feed.actions.report

import com.wemeal.data.model.BaseModel

data class Result(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val post: String,
    val reason: String,
    val refPostModel: String,
    val updatedAt: String,
    val user: String
) : BaseModel()