package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.onboarding.FollowFoodieDataSource
import retrofit2.Response

class FollowFoodieDataSourceImpl(private val apiServices: ApiServices) : FollowFoodieDataSource {
    override suspend fun followFoodie(
        foodieId: String
    ): Response<SuccessModel> {
        return apiServices.followFoodie(foodieId = foodieId)
    }
}