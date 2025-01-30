package com.wemeal.domain.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.countries.CountriesModel

interface GetCountriesRepository {
    suspend fun getCountries(page: Int): Resource<CountriesModel>
}