package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.create.AwsResponseModel
import com.wemeal.data.repository.datasource.feed.UploadImagesDataSource
import com.wemeal.domain.repository.feed.UploadImagesRepository
import org.json.JSONObject
import retrofit2.Response
import java.io.File

class UploadImagesRepositoryImpl(private val uploadImagesDataSource: UploadImagesDataSource) :
    UploadImagesRepository {
    override suspend fun upload(file: File): Resource<AwsResponseModel> {
        return responseToResource(uploadImagesDataSource.upload(file = file))
    }

    private fun responseToResource(response: Response<AwsResponseModel>): Resource<AwsResponseModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(parseErrorMessage(response.errorBody()?.string()))
    }

    private fun parseErrorMessage(error: String?): String? {
        return try {
            val json = JSONObject(error ?: "")
            json.getString("message")
        } catch (e: Exception) {
            e.message
        }
    }
}