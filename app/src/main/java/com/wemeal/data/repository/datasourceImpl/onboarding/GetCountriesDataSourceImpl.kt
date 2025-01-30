package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.repository.datasource.onboarding.GetCountriesDataSource
import retrofit2.Response

class GetCountriesDataSourceImpl(private val apiServices: ApiServices) : GetCountriesDataSource {
    override suspend fun getCountries(page: Int): Response<CountriesModel> {
        return apiServices.getCountries(page = page)
    }
}