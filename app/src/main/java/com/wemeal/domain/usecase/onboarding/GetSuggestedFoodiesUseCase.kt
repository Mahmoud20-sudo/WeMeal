package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.GetSuggestedFoodiesRepository

class GetSuggestedFoodiesUseCase(private val getSuggestedFoodiesRepository: GetSuggestedFoodiesRepository) {
    suspend fun execute() = getSuggestedFoodiesRepository.getFoodies()
}