package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.onboarding.PatchUserDataSource
import retrofit2.Response

class PatchUserDataSourceImpl(private val apiServices: ApiServices) : PatchUserDataSource {
    override suspend fun updateUser(): Response<SuccessModel> {
        return apiServices.patchUser()
    }
}
