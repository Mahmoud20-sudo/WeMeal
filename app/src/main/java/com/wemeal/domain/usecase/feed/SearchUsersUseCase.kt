package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.SearchUsersRepository

class SearchUsersUseCase(private val searchUsersRepository: SearchUsersRepository) {
    suspend fun execute(search: String, excludesIds: String) = searchUsersRepository.search(search = search, excludesIds = excludesIds)
}