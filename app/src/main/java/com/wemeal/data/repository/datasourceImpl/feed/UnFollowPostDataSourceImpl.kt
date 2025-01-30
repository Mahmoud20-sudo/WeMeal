package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.feed.UnFollowPostDataSource
import retrofit2.Response

class UnFollowPostDataSourceImpl(private val apiServices: ApiServices) : UnFollowPostDataSource {
    override suspend fun unfollow(
        id: String
    ): Response<SuccessModel> {
        return apiServices.unfollowPost(id = id)
    }
}
