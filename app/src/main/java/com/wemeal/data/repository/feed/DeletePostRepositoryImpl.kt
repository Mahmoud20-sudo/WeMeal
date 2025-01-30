package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.delete.DeleteResponseModel
import com.wemeal.data.repository.datasource.feed.DeletePostDataSource
import com.wemeal.domain.repository.feed.DeletePostRepository
import org.json.JSONObject
import retrofit2.Response

class DeletePostRepositoryImpl(private val deletePostDataSource: DeletePostDataSource) :
    DeletePostRepository {
    override suspend fun deletePost(
        id: String
    ): Resource<DeleteResponseModel> {
        return responseToResource(
            deletePostDataSource.delete(id = id)
        )
    }

    private fun responseToResource(response: Response<DeleteResponseModel>): Resource<DeleteResponseModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return when (result.result.error != null) {
                    true -> Resource.Error(
                        when {
                            result.result.error?.en?.contains("not found") == false -> result.result.error?.en
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