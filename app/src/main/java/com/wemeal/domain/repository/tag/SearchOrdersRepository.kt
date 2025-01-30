package com.wemeal.domain.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.orders.SearchedOrders

interface SearchOrdersRepository {
    suspend fun search(search: String): Resource<SearchedOrders>
}