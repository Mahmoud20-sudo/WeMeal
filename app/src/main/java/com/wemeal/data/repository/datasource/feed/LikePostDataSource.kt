package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import retrofit2.Response

interface LikePostDataSource {
    suspend fun like(id: String): Response<LikeResponseModel>
}