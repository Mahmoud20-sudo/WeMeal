package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.onboarding.countries.CountriesModel
import retrofit2.Response

interface GetAreasDataSource {
    suspend fun getAreas(cityId: String, searchText: String, page: Int): Response<CountriesModel>
}