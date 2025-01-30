package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.FollowBrandRepository

class FollowBrandUseCase(private val followBrandRepository: FollowBrandRepository) {
    suspend fun execute(brandId: String) = followBrandRepository.followBrand(brandId = brandId)
}