package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.onboarding.suggested_brands.BrandsModel
import com.wemeal.data.repository.datasource.onboarding.GetSuggestedBrandsDataSource
import retrofit2.Response

class GetSuggestedBrandsDataSourceImpl(private val apiServices: ApiServices) :
    GetSuggestedBrandsDataSource {
    override suspend fun getBrands(): Response<BrandsModel> {
        return apiServices.getSuggestedBrands()
    }
}