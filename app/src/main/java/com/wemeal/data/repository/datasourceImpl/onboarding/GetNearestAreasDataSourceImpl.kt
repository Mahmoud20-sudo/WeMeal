package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.onboarding.nearest.NearestModel
import com.wemeal.data.repository.datasource.onboarding.GetNearestAreasDataSource
import retrofit2.Response

class GetNearestAreasDataSourceImpl(private val apiServices: ApiServices) :
    GetNearestAreasDataSource {
    override suspend fun nearestLocation(lat: Double, lng: Double): Response<NearestModel> {
        return apiServices.nearestLocation(lat, lng)
    }
}