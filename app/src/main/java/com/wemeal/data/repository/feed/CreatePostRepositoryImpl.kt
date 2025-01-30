package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.create.AwsRequestModel
import com.wemeal.data.model.main.create.CreatePostResponseModel
import com.wemeal.data.repository.datasource.feed.CreatePostDataSource
import com.wemeal.domain.repository.feed.CreatePostRepository
import org.json.JSONObject
import retrofit2.Response

class CreatePostRepositoryImpl(private val createPostDataSource: CreatePostDataSource) :
    CreatePostRepository {
    override suspend fun createPost(
        text: String,
        taggedObject: String?,
        objectType: String?,
        shareToBrand: Boolean?,
        images: List<AwsRequestModel>?
    ): Resource<CreatePostResponseModel> {
        return responseToResource(
            createPostDataSource.create(
                text = text,
                taggedObject = taggedObject,
                objectType = objectType,
                shareToBrand = shareToBrand,
                images = images
            )
        )
    }

    private fun responseToResource(response: Response<CreatePostResponseModel>): Resource<CreatePostResponseModel> {
        if (response.isSuccessful) {
            response.body()?.let { result -> return Resource.Success(result) }
        }
        return Resource.Error(parseErrorMessage(response.errorBody()?.string()))
    }

    private fun parseErrorMessage(error: String?): String? {
        return try {
            val json = JSONObject(error ?: "")
            json.getString("type")
        } catch (e: Exception) {
            e.message
        }
    }
}