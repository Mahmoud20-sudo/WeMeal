package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import com.wemeal.data.repository.datasource.feed.LikePostDataSource
import com.wemeal.domain.repository.feed.LikePostRepository
import org.json.JSONObject
import retrofit2.Response

class LikePostRepositoryImpl(private val likePostDataSource: LikePostDataSource) :
    LikePostRepository {
    override suspend fun likePost(
        id: String
    ): Resource<LikeResponseModel> {
        return responseToResource(
            likePostDataSource.like(id = id)
        )
    }

    private fun responseToResource(response: Response<LikeResponseModel>): Resource<LikeResponseModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return when(result.result?.error != null){
                    true -> Resource.Error(
                        when {
                            result.result?.error?.en?.contains("not found") == false -> result.result?.error?.en
                            else -> "removed"
                        }
                    )
                    false -> Resource.Success(result)
                }
            }
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