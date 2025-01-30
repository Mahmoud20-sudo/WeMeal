package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.SearchUsersModel
import retrofit2.Response

interface SearchUsersDataSource {
    suspend fun search(search: String, excludesIds: String): Response<SearchUsersModel>
}