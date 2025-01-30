package com.wemeal.data.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.repository.datasource.onboarding.GetCountriesDataSource
import com.wemeal.domain.repository.onboarding.GetCountriesRepository
import retrofit2.Response

class GetCountriesRepositoryImpl(private val getCountriesDataSource: GetCountriesDataSource) :
    GetCountriesRepository {
    override suspend fun getCountries(page: Int): Resource<CountriesModel> {
        return responseToResource(getCountriesDataSource.getCountries(page = page))
    }

    private fun responseToResource(response: Response<CountriesModel>): Resource<CountriesModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}