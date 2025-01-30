package com.wemeal.data.repository.datasource.tag

import com.wemeal.data.model.main.tagged.orders.SearchedOrders
import retrofit2.Response

interface SearchOrdersDataSource {
    suspend fun search(search: String): Response<SearchedOrders>
}