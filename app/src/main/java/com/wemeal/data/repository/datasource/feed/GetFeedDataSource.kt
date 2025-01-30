package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.feed.FeedModel
import retrofit2.Response

interface GetFeedDataSource {
    suspend fun getFeed(afterId: String?): Response<FeedModel>
}