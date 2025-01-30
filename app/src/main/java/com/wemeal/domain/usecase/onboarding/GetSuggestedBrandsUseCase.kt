package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.GetSuggestedBrandsRepository

class GetSuggestedBrandsUseCase(private val getSuggestedBrandsRepository: GetSuggestedBrandsRepository) {
    suspend fun execute() = getSuggestedBrandsRepository.getBrands()
}