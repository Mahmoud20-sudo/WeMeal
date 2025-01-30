package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.UnFollowFoodieRepository

class UnFollowFoodieUseCase(private val unFollowFoodieRepository: UnFollowFoodieRepository) {
    suspend fun execute(foodieId: String) = unFollowFoodieRepository.unFollowFoodie(foodieId = foodieId)
}