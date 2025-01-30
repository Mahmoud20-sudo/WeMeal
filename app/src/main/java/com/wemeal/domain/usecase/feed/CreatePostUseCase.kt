package com.wemeal.domain.usecase.feed

import com.wemeal.data.model.main.create.AwsRequestModel
import com.wemeal.domain.repository.feed.CreatePostRepository

class CreatePostUseCase(private val createPostRepository: CreatePostRepository) {
    suspend fun execute(
        text: String,
        taggedObject : String?,
        objectType : String?,
        shareToBrand: Boolean?,
        images: List<AwsRequestModel>?
    ) = createPostRepository.createPost(
        text = text,
        taggedObject = taggedObject,
        objectType = objectType,
        shareToBrand = shareToBrand,
        images = images
    )
}