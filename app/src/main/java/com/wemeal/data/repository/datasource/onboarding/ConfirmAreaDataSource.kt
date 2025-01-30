package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.SuccessModel
import retrofit2.Response

interface ConfirmAreaDataSource {
    suspend fun confirmArea(areaModel : com.wemeal.data.model.onboarding.nearest.Result): Response<SuccessModel>
}