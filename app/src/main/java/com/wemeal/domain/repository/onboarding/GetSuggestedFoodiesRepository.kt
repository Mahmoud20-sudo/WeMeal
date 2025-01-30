package com.wemeal.domain.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel

interface GetSuggestedFoodiesRepository {
    suspend fun getFoodies(): Resource<FoodiesModel>
}