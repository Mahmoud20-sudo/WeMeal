package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.repository.datasource.onboarding.GetSubAreasDataSource
import retrofit2.Response

class GetSubAreasDataSourceImpl(private val apiServices: ApiServices) : GetSubAreasDataSource {
    override suspend fun getSubAreas(
        areaId: String,
        searchText: String,
        page: Int
    ): Response<CountriesModel> {
        return apiServices.getSubAreas(areaName = areaId, page = page, search = searchText)
    }
}