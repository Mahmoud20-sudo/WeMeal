package com.wemeal.data.repository.datasource.onboarding

import com.wemeal.data.model.onboarding.countries.CountriesModel
import retrofit2.Response

interface GetCountriesDataSource {
    suspend fun getCountries(page: Int): Response<CountriesModel>
}