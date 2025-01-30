package com.wemeal.data.repository.datasourceImpl.tag

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.tagged.offers.SearchedOffers
import com.wemeal.data.repository.datasource.tag.SearchOffersDataSource
import retrofit2.Response

class SearchOffesDataSourceImpl(private val apiServices: ApiServices) : SearchOffersDataSource {
    override suspend fun search(
        search: String
    ): Response<SearchedOffers> {
        return apiServices.searchOffers(search = search)
    }
}
