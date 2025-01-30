package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.onboarding.countries.CountriesModel
import retrofit2.Response

interface UnFollowBrandDataSource {
    suspend fun unFollowBrand(brandId: String): Response<SuccessModel>
}