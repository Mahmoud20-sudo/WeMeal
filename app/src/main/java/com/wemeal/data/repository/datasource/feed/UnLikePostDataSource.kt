package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.feed.actions.unlike.UnLikeResponseModel
import retrofit2.Response

interface UnLikePostDataSource {
    suspend fun unlike(id: String): Response<UnLikeResponseModel>
}