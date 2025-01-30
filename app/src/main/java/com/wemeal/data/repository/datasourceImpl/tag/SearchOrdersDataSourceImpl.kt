package com.wemeal.data.repository.datasourceImpl.tag

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.tagged.orders.SearchedOrders
import com.wemeal.data.repository.datasource.tag.SearchOrdersDataSource
import retrofit2.Response

class SearchOrdersDataSourceImpl(private val apiServices: ApiServices) : SearchOrdersDataSource {
    override suspend fun search(
        search: String
    ): Response<SearchedOrders> {
        return apiServices.searchOrders(search = search)
    }
}
