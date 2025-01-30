package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import com.wemeal.data.repository.datasource.feed.LikePostDataSource
import retrofit2.Response

class LikePostDataSourceImpl(private val apiServices: ApiServices) : LikePostDataSource {
    override suspend fun like(
        id: String
    ): Response<LikeResponseModel> {
        return apiServices.likePost(id = id)
    }
}
