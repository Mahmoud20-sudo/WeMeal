package com.wemeal.domain.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.brands.SearchedBrands

interface SearchBrandsRepository {
    suspend fun search(search: String): Resource<SearchedBrands>
}