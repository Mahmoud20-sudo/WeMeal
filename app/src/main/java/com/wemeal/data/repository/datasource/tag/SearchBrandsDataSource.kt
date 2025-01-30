package com.wemeal.data.repository.datasource.tag

import com.wemeal.data.model.main.tagged.brands.SearchedBrands
import retrofit2.Response

interface SearchBrandsDataSource {
    suspend fun search(search: String): Response<SearchedBrands>
}