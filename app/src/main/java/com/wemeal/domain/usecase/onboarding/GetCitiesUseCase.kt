package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.GetCitiesRepository

class GetCitiesUseCase(private val getCitiesRepository: GetCitiesRepository) {
    suspend fun execute(page: Int, searchText:String) = getCitiesRepository.getCities(page = page, searchText = searchText)
}