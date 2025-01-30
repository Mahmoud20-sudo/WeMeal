package com.wemeal.domain.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel

interface ConfirmAreaRepository {
    suspend fun confirmArea(areaModel: com.wemeal.data.model.onboarding.nearest.Result): Resource<SuccessModel>
}