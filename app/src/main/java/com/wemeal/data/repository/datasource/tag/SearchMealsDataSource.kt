package com.wemeal.data.repository.datasource.tag

import com.wemeal.data.model.main.tagged.products.SearchedProducts
import retrofit2.Response

interface SearchMealsDataSource {
    suspend fun search(search: String): Response<SearchedProducts>
}