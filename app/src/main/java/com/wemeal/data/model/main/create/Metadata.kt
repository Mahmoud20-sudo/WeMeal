package com.wemeal.data.model.main.create

data class Metadata(
    val attempts: Int,
    val httpStatusCode: Int,
    val requestId: String,
    val totalRetryDelay: Int
)