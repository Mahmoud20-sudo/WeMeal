package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.GetNearestAreasRepository

class GetNearestAreasUseCase(private val getNearestAreasRepository: GetNearestAreasRepository) {
    suspend fun execute(lat: Double, lng: Double) = getNearestAreasRepository.getNearestArea(lat, lng)
}