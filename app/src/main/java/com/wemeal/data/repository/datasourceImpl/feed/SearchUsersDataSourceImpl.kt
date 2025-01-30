package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.SearchUsersModel
import com.wemeal.data.repository.datasource.feed.SearchUsersDataSource
import retrofit2.Response

class SearchUsersDataSourceImpl(private val apiServices: ApiServices) : SearchUsersDataSource {
    override suspend fun search(
        search: String,
        excludesIds: String
    ): Response<SearchUsersModel> {
        return apiServices.searchUsers(search = search, excludeIds = excludesIds)
    }
}
