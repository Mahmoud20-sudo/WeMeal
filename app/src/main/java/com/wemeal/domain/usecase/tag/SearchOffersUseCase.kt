package com.wemeal.domain.usecase.tag

import com.wemeal.domain.repository.feed.CreatePostRepository
import com.wemeal.domain.repository.tag.SearchBrandsRepository
import com.wemeal.domain.repository.tag.SearchOffersRepository

class SearchOffersUseCase(private val searchOffersRepository: SearchOffersRepository) {
    suspend fun execute(search: String) = searchOffersRepository.search(search = search)
}