package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.FeedDetailsModel
import com.wemeal.data.model.main.feed.Result
import com.wemeal.data.repository.datasource.feed.GetPostDataSource
import com.wemeal.domain.repository.feed.GetPostRepository
import org.json.JSONObject
import retrofit2.Response

class GetPostRepositoryImpl(private val getPostDataSource: GetPostDataSource) :
    GetPostRepository {
    override suspend fun getPost(
        id: String
    ): Resource<FeedDetailsModel> {
        return responseToResource(
            getPostDataSource.get(id = id)
        )
    }

    private fun responseToResource(response: Response<FeedDetailsModel>): Resource<FeedDetailsModel> {
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