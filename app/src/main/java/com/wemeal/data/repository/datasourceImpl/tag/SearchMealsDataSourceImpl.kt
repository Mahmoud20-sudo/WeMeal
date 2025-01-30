package com.wemeal.data.repository.datasourceImpl.tag

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.tagged.products.SearchedProducts
import com.wemeal.data.repository.datasource.tag.SearchMealsDataSource
import retrofit2.Response

class SearchMealsDataSourceImpl(private val apiServices: ApiServices) : SearchMealsDataSource {
    override suspend fun search(
        search: String
    ): Response<SearchedProducts> {
        return apiServices.searchMeals(search = search)
    }
}
