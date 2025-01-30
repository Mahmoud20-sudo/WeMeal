package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.onboarding.FollowBrandDataSource
import retrofit2.Response

class FollowBrandDataSourceImpl(private val apiServices: ApiServices) : FollowBrandDataSource {
    override suspend fun followBrand(
        brandId: String
    ): Response<SuccessModel> {
        return apiServices.followBrand(brandId = brandId)
    }
}