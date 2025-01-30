package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.unlike.UnLikeResponseModel
import com.wemeal.data.repository.datasource.feed.UnLikePostDataSource
import com.wemeal.domain.repository.feed.UnLikePostRepository
import org.json.JSONObject
import retrofit2.Response

class UnLikePostRepositoryImpl(private val unLikePostDataSource: UnLikePostDataSource) :
    UnLikePostRepository {
    override suspend fun unLikePost(
        id: String
    ): Resource<UnLikeResponseModel> {
        return responseToResource(
            unLikePostDataSource.unlike(id = id)
        )
    }

    private fun responseToResource(response: Response<UnLikeResponseModel>): Resource<UnLikeResponseModel> {
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