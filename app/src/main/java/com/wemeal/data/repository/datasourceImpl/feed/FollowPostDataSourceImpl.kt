package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.feed.FollowPostDataSource
import retrofit2.Response

class FollowPostDataSourceImpl(private val apiServices: ApiServices) : FollowPostDataSource {
    override suspend fun follow(
        id: String
    ): Response<SuccessModel> {
        return apiServices.followPost(id = id)
    }
}
