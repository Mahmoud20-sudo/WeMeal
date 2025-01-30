package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel
import retrofit2.Response

interface GetSuggestedFoodiesDataSource {
    suspend fun getFoodies(): Response<FoodiesModel>
}