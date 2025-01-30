package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.UnFollowBrandRepository

class UnFollowBrandUseCase(private val unFollowBrandRepository: UnFollowBrandRepository) {
    suspend fun execute(brandId: String) = unFollowBrandRepository.unFollowBrand(brandId = brandId)
}