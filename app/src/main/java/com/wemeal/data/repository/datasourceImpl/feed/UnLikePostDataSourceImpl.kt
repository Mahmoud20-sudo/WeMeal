package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.feed.actions.unlike.UnLikeResponseModel
import com.wemeal.data.repository.datasource.feed.UnLikePostDataSource
import retrofit2.Response

class UnLikePostDataSourceImpl(private val apiServices: ApiServices) : UnLikePostDataSource {
    override suspend fun unlike(
        id: String
    ): Response<UnLikeResponseModel> {
        return apiServices.unLikePost(id = id)
    }
}
