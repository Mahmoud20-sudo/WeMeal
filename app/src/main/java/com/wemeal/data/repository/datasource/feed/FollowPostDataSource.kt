package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import retrofit2.Response

interface FollowPostDataSource {
    suspend fun follow(id: String): Response<SuccessModel>
}