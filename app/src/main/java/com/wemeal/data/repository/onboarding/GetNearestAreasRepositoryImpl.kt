package com.wemeal.data.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.nearest.NearestModel
import com.wemeal.data.repository.datasource.onboarding.GetNearestAreasDataSource
import com.wemeal.domain.repository.onboarding.GetNearestAreasRepository
import retrofit2.Response

class GetNearestAreasRepositoryImpl(private val getNearestAreasDataSource: GetNearestAreasDataSource) :
    GetNearestAreasRepository {
    override suspend fun getNearestArea(lat: Double, lng: Double): Resource<NearestModel> {
        return responseToResource(getNearestAreasDataSource.nearestLocation(lat, lng))
    }

    private fun responseToResource(response: Response<NearestModel>): Resource<NearestModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}