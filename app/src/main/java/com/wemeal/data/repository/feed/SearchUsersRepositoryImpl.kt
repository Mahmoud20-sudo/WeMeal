package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.SearchUsersModel
import com.wemeal.data.repository.datasource.feed.SearchUsersDataSource
import com.wemeal.domain.repository.feed.SearchUsersRepository
import retrofit2.Response

class SearchUsersRepositoryImpl(private val searchUsersDataSource: SearchUsersDataSource) :
    SearchUsersRepository {
    override suspend fun search(
        search: String,
        excludesIds: String
    ): Resource<SearchUsersModel> {
        return responseToResource(searchUsersDataSource.search(search = search, excludesIds = excludesIds))
    }

    private fun responseToResource(response: Response<SearchUsersModel>): Resource<SearchUsersModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}