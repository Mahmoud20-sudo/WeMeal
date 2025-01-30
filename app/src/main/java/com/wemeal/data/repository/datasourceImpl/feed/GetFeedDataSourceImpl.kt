package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.feed.FeedModel
import com.wemeal.data.repository.datasource.feed.GetFeedDataSource
import retrofit2.Response

class GetFeedDataSourceImpl(private val apiServices: ApiServices) : GetFeedDataSource {
    override suspend fun getFeed(afterId: String?): Response<FeedModel> {
        return apiServices.getFeed(afterId = afterId)
    }
}
