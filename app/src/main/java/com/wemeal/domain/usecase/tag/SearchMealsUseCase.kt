package com.wemeal.domain.usecase.tag

import com.wemeal.domain.repository.feed.CreatePostRepository
import com.wemeal.domain.repository.tag.SearchBrandsRepository
import com.wemeal.domain.repository.tag.SearchMealsRepository

class SearchMealsUseCase(private val searchMealsRepository: SearchMealsRepository) {
    suspend fun execute(search: String) = searchMealsRepository.search(search = search)
}