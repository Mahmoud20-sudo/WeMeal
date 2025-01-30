package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.feed.FollowPostDataSource
import com.wemeal.domain.repository.feed.FollowPostRepository
import org.json.JSONObject
import retrofit2.Response

class FollowPostRepositoryImpl(private val followPostDataSource: FollowPostDataSource) :
    FollowPostRepository {
    override suspend fun followPost(
        id: String
    ): Resource<SuccessModel> {
        return responseToResource(
            followPostDataSource.follow(id = id)
        )
    }

    private fun responseToResource(response: Response<SuccessModel>): Resource<SuccessModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return when (result.error != null) {
                    true -> Resource.Error(
                        when {
                            result.error?.en?.contains("not found") == false -> result.error?.en
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