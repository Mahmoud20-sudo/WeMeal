package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.repository.datasource.onboarding.GetAreasDataSource
import retrofit2.Response

class GetAreasDataSourceImpl(private val apiServices: ApiServices) : GetAreasDataSource {
    override suspend fun getAreas(
        cityId: String,
        searchText: String,
        page: Int
    ): Response<CountriesModel> {
        return apiServices.getAreas(cityId = cityId, page = page, search = searchText)
    }
}