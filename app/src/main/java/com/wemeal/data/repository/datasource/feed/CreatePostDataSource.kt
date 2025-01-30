package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.create.AwsRequestModel
import com.wemeal.data.model.main.create.CreatePostResponseModel
import retrofit2.Response

interface CreatePostDataSource {
    suspend fun create(
        text: String,
        taggedObject: String?,
        objectType : String?,
        shareToBrand: Boolean?,
        images: List<AwsRequestModel>?
    ): Response<CreatePostResponseModel>
}