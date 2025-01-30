package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.onboarding.countries.CountriesModel
import retrofit2.Response

interface GetCitiesDataSource {
    suspend fun getCities(page: Int,searchText: String): Response<CountriesModel>
}