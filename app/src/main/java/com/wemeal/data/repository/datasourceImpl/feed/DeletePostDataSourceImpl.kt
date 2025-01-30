package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.feed.actions.delete.DeleteResponseModel
import com.wemeal.data.repository.datasource.feed.DeletePostDataSource
import retrofit2.Response

class DeletePostDataSourceImpl(private val apiServices: ApiServices) : DeletePostDataSource {
    override suspend fun delete(
        id: String
    ): Response<DeleteResponseModel> {
        return apiServices.deletePost(id = id)
    }
}
