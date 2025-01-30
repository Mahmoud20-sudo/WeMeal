package com.wemeal.data.repository.datasource.tag

import com.wemeal.data.model.main.tagged.offers.SearchedOffers
import retrofit2.Response

interface SearchOffersDataSource {
    suspend fun search(search: String): Response<SearchedOffers>
}