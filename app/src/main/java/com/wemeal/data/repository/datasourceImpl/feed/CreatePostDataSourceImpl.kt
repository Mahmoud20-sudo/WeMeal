package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.create.AwsRequestModel
import com.wemeal.data.model.main.create.CreatePostRequestModel
import com.wemeal.data.model.main.create.CreatePostResponseModel
import com.wemeal.data.repository.datasource.feed.CreatePostDataSource
import retrofit2.Response

class CreatePostDataSourceImpl(private val apiServices: ApiServices) : CreatePostDataSource {
    override suspend fun create(
        text: String,
        taggedObject : String?,
        objectType : String?,
        shareToBrand: Boolean?,
        images: List<AwsRequestModel>?
    ): Response<CreatePostResponseModel> {
        val createPostRequestModel = CreatePostRequestModel(
            body = text,
            taggedObject = taggedObject,
            objectType = objectType,
            shareToBrand = shareToBrand,
            images = images
        )
        return apiServices.createPost(createPostRequestModel = createPostRequestModel)
    }
}
