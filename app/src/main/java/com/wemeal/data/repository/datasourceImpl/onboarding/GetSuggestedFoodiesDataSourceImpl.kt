package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel
import com.wemeal.data.repository.datasource.onboarding.GetSuggestedFoodiesDataSource
import retrofit2.Response

class GetSuggestedFoodiesDataSourceImpl(private val apiServices: ApiServices) :
    GetSuggestedFoodiesDataSource {
    override suspend fun getFoodies(): Response<FoodiesModel> {
        return apiServices.getSuggestedFoodies()
    }
}