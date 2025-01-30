package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.onboarding.suggested_brands.BrandsModel
import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel
import retrofit2.Response

interface GetSuggestedBrandsDataSource {
    suspend fun getBrands(): Response<BrandsModel>
}