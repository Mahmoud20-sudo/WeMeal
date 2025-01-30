package com.wemeal.domain.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.nearest.NearestModel

interface GetNearestAreasRepository {
    suspend fun getNearestArea(lat: Double, lng: Double): Resource<NearestModel>
}