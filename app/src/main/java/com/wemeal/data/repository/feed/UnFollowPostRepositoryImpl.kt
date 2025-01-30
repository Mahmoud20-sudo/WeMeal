package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.feed.UnFollowPostDataSource
import com.wemeal.domain.repository.feed.UnFollowPostRepository
import org.json.JSONObject
import retrofit2.Response

class UnFollowPostRepositoryImpl(private val unFollowPostDataSource: UnFollowPostDataSource) :
    UnFollowPostRepository {
    override suspend fun unfollowPost(
        id: String
    ): Resource<SuccessModel> {
        return responseToResource(
            unFollowPostDataSource.unfollow(id = id)
        )
    }

    private fun responseToResource(response: Response<SuccessModel>): Resource<SuccessModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return when(result.error != null){
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