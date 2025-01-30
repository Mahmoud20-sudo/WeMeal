package com.wemeal.data.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel
import com.wemeal.data.repository.datasource.onboarding.GetSuggestedFoodiesDataSource
import com.wemeal.domain.repository.onboarding.GetSuggestedFoodiesRepository
import retrofit2.Response

class GetSuggestedFoodiesRepositoryImpl(private val getSuggestedFoodiesDataSource: GetSuggestedFoodiesDataSource) :
    GetSuggestedFoodiesRepository {
    override suspend fun getFoodies(
    ): Resource<FoodiesModel> {
        return responseToResource(
            getSuggestedFoodiesDataSource.getFoodies()
        )
    }

    private fun responseToResource(response: Response<FoodiesModel>): Resource<FoodiesModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}