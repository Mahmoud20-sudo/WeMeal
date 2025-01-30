package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.feed.FeedDetailsModel
import com.wemeal.data.model.main.feed.Result
import com.wemeal.data.repository.datasource.feed.GetPostDataSource
import retrofit2.Response

class GetPostDataSourceImpl(private val apiServices: ApiServices) : GetPostDataSource {
    override suspend fun get(
        id: String
    ): Response<FeedDetailsModel> {
        return apiServices.getPostById(id = id)
    }
}
