package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.FollowFoodieRepository

class FollowFoodieUseCase(private val followFoodieRepository: FollowFoodieRepository) {
    suspend fun execute(foodieId: String) = followFoodieRepository.followFoodie(foodieId = foodieId)
}