package com.wemeal.domain.usecase.tag

import com.wemeal.domain.repository.feed.CreatePostRepository
import com.wemeal.domain.repository.tag.SearchBrandsRepository

class SearchBrandsUseCase(private val searchBrandsRepository: SearchBrandsRepository) {
    suspend fun execute(search: String) = searchBrandsRepository.search(search = search)
}