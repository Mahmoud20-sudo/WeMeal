package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.onboarding.nearest.NearestModel
import retrofit2.Response

interface GetNearestAreasDataSource {
    suspend fun nearestLocation(lat: Double, lng: Double): Response<NearestModel>
}