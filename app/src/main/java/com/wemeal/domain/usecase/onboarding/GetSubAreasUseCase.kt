package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.GetSubAreasRepository

class GetSubAreasUseCase(private val getSubAreasRepository: GetSubAreasRepository) {
    suspend fun execute(
        areaId: String,
        searchText: String, page: Int
    ) = getSubAreasRepository.getSubAreas(areaId = areaId, searchText = searchText, page = page)
}