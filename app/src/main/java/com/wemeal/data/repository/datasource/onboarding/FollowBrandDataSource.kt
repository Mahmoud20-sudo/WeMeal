package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.onboarding.countries.CountriesModel
import retrofit2.Response

interface FollowBrandDataSource {
    suspend fun followBrand(brandId: String): Response<SuccessModel>
}