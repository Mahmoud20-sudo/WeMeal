package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.onboarding.UnFollowBrandDataSource
import retrofit2.Response

class UnFollowBrandDataSourceImpl(private val apiServices: ApiServices) :
    UnFollowBrandDataSource {
    override suspend fun unFollowBrand(
        brandId: String
    ): Response<SuccessModel> {
        return apiServices.unFollowBrand(brandId = brandId)
    }
}