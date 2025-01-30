package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.onboarding.UnFollowFoodieDataSource
import retrofit2.Response

class UnFollowFoodieDataSourceImpl(private val apiServices: ApiServices) :
    UnFollowFoodieDataSource {
    override suspend fun unFollowFoodie(
        foodieId: String
    ): Response<SuccessModel> {
        return apiServices.unFollowFoodie(foodieId = foodieId)
    }
}