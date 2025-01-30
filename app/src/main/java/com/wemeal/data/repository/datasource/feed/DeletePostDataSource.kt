package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.feed.actions.delete.DeleteResponseModel
import retrofit2.Response

interface DeletePostDataSource {
    suspend fun delete(id: String): Response<DeleteResponseModel>
}