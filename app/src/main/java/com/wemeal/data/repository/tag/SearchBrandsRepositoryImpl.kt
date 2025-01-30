package com.wemeal.data.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.brands.SearchedBrands
import com.wemeal.data.repository.datasource.tag.SearchBrandsDataSource
import com.wemeal.domain.repository.tag.SearchBrandsRepository
import org.json.JSONObject
import retrofit2.Response

class SearchBrandsRepositoryImpl(private val searchBrandsDataSource: SearchBrandsDataSource) :
    SearchBrandsRepository {
    override suspend fun search(
        search: String
    ): Resource<SearchedBrands> {
        return responseToResource(
            searchBrandsDataSource.search(
                search = search
            )
        )
    }

    private fun responseToResource(response: Response<SearchedBrands>): Resource<SearchedBrands> {
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