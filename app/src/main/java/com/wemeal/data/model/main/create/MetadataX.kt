package com.wemeal.data.model.main.create

data class MetadataX(
    val attempts: Int,
    val extendedRequestId: String,
    val httpStatusCode: Int,
    val totalRetryDelay: Int
)