package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.feed.FeedDetailsModel
import retrofit2.Response

interface GetPostDataSource {
    suspend fun get(id: String): Response<FeedDetailsModel>
}