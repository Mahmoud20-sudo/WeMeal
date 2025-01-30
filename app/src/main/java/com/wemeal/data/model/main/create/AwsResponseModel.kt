package com.wemeal.data.model.main.create

data class AwsResponseModel(
    val elapsedTime: String?,
    val height: Int?,
    val imageUrl: String?,
    val isAuthorized: Boolean?,
    val message: String?,
    val model: String?,
    val moderationLabels: ModerationLabels?,
    val newContentType: String?,
    val newSize: String?,
    val orientation: String?,
    val originalContentType: String?,
    val originalSize: String?,
    val pictureType: String?,
    val role: String?,
    val s3Response: S3Response?,
    val uniqueName: String?,
    val webpQualityMessage: String?,
    val width: Int?
)