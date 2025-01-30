package com.wemeal.data.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.orders.SearchedOrders
import com.wemeal.data.repository.datasource.tag.SearchOrdersDataSource
import com.wemeal.domain.repository.tag.SearchOrdersRepository
import org.json.JSONObject
import retrofit2.Response

class SearchOrdersRepositoryImpl(private val searchOrdersDataSource: SearchOrdersDataSource) :
    SearchOrdersRepository {
    override suspend fun search(
        search: String
    ): Resource<SearchedOrders> {
        return responseToResource(
            searchOrdersDataSource.search(
                search = search
            )
        )
    }

    private fun responseToResource(response: Response<SearchedOrders>): Resource<SearchedOrders> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(parseErrorMessage(response.errorBody()?.string()))
    }

    private fun parseErrorMessage(error: String?) : String?{
        return try {
            val json = JSONObject(error ?: "")
            json.getString("type")
        }catch (e: Exception){
            e.message
        }
    }
}