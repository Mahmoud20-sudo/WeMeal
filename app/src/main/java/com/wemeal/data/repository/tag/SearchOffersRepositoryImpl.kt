package com.wemeal.data.repository.tag

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.tagged.offers.SearchedOffers
import com.wemeal.data.repository.datasource.tag.SearchOffersDataSource
import com.wemeal.domain.repository.tag.SearchOffersRepository
import org.json.JSONObject
import retrofit2.Response

class SearchOffersRepositoryImpl(private val searchOffersDataSource: SearchOffersDataSource) :
    SearchOffersRepository {
    override suspend fun search(
        search: String
    ): Resource<SearchedOffers> {
        return responseToResource(
            searchOffersDataSource.search(
                search = search
            )
        )
    }

    private fun responseToResource(response: Response<SearchedOffers>): Resource<SearchedOffers> {
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