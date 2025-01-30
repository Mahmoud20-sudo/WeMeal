package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.SuccessModel
import retrofit2.Response

interface PatchUserDataSource {
    suspend fun updateUser(): Response<SuccessModel>
}