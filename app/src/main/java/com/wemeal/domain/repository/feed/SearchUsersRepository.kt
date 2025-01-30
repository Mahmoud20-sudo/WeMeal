package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.SearchUsersModel

interface SearchUsersRepository {
    suspend fun search(search: String, excludesIds: String): Resource<SearchUsersModel>
}