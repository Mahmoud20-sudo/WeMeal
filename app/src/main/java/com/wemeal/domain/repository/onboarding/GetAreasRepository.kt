package com.wemeal.domain.repository.onboarding

import androidx.paging.PagingSource
import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.model.onboarding.countries.Result

interface GetAreasRepository {
    suspend fun getAreas(cityId: String, searchText: String, page: Int): Resource<CountriesModel>
}