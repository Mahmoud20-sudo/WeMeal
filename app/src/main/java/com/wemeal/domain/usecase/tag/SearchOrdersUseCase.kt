package com.wemeal.domain.usecase.tag

import com.wemeal.domain.repository.feed.CreatePostRepository
import com.wemeal.domain.repository.tag.SearchBrandsRepository
import com.wemeal.domain.repository.tag.SearchOrdersRepository

class SearchOrdersUseCase(private val searchOrdersRepository: SearchOrdersRepository) {
    suspend fun execute(search: String) = searchOrdersRepository.search(search = search)
}