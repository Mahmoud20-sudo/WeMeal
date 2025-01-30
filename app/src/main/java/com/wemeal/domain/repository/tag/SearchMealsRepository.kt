package com.wemeal.domain.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.products.SearchedProducts

interface SearchMealsRepository {
    suspend fun search(search: String): Resource<SearchedProducts>
}