package com.wemeal.data.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.products.SearchedProducts
import com.wemeal.data.repository.datasource.tag.SearchMealsDataSource
import com.wemeal.domain.repository.tag.SearchMealsRepository
import org.json.JSONObject
import retrofit2.Response

class SearchMealsRepositoryImpl(private val searchMealsDataSource: SearchMealsDataSource) :
    SearchMealsRepository {
    override suspend fun search(
        search: String
    ): Resource<SearchedProducts> {
        return responseToResource(
            searchMealsDataSource.search(
                search = search
            )
        )
    }

    private fun responseToResource(response: Response<SearchedProducts>): Resource<SearchedProducts> {
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