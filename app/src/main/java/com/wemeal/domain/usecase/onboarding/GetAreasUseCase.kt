package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.GetAreasRepository

class GetAreasUseCase(private val getAreasRepository: GetAreasRepository) {
    suspend fun execute(
        cityId: String,
        searchText: String, page: Int
    ) = getAreasRepository.getAreas(cityId = cityId, searchText = searchText, page = page)
}