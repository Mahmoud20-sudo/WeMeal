package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.FeedModel
import com.wemeal.data.repository.datasource.feed.GetFeedDataSource
import com.wemeal.domain.repository.feed.GetFeedRepository
import org.json.JSONObject
import retrofit2.Response

class GetFeedRepositoryImpl(private val getFeedDataSource: GetFeedDataSource) :
    GetFeedRepository {
    override suspend fun getFeed(afterId: String?): Resource<FeedModel> {
        return responseToResource(getFeedDataSource.getFeed(afterId = afterId))
    }

    private fun responseToResource(response: Response<FeedModel>): Resource<FeedModel> {
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
            json.getString("type")
        } catch (e: Exception) {
            e.message
        }
    }
}