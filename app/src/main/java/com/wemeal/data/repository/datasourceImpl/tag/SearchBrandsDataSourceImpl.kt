package com.wemeal.data.repository.datasourceImpl.tag

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.tagged.brands.SearchedBrands
import com.wemeal.data.repository.datasource.tag.SearchBrandsDataSource
import retrofit2.Response

class SearchBrandsDataSourceImpl(private val apiServices: ApiServices) : SearchBrandsDataSource {
    override suspend fun search(
        search: String
    ): Response<SearchedBrands> {
        return apiServices.searchBrands(search = search)
    }
}
