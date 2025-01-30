package com.wemeal.domain.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.offers.SearchedOffers

interface SearchOffersRepository {
    suspend fun search(search: String): Resource<SearchedOffers>
}