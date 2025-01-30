package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.create.AwsRequestModel
import com.wemeal.data.model.main.create.CreatePostResponseModel

interface CreatePostRepository {
    suspend fun createPost(
        text: String,
        taggedObject : String?,
        objectType : String?,
        shareToBrand :Boolean?,
        images: List<AwsRequestModel>?
    ): Resource<CreatePostResponseModel>
}