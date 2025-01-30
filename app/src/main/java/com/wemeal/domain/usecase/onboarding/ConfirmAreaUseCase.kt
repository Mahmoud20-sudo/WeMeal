package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.ConfirmAreaRepository

class ConfirmAreaUseCase(private val confirmAreaRepository: ConfirmAreaRepository) {
    suspend fun execute(areaModel: com.wemeal.data.model.onboarding.nearest.Result) = confirmAreaRepository.confirmArea(areaModel = areaModel)
}