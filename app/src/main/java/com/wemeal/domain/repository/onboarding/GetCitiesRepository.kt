package com.wemeal.domain.repository.onboarding

import androidx.paging.PagingSource
import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.model.onboarding.countries.Result

interface GetCitiesRepository {
    suspend fun getCities(page: Int, searchText: String): Resource<CountriesModel>
}