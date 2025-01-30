package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.repository.datasource.onboarding.GetCitiesDataSource
import retrofit2.Response

class GetCitiesDataSourceImpl(private val apiServices: ApiServices) : GetCitiesDataSource {
    override suspend fun getCities(page: Int,searchText: String): Response<CountriesModel> {
        return apiServices.getCities(page = page, search = searchText)
    }
}
//class GetCitiesDataSourceImpl @Inject constructor(
//    @Assisted val apiServices: ApiServices?,
//) : PagingSource<Int, Result>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
//        try {
//            // Start refresh at page 1 if undefined.
//            val nextPage = params.key ?: 1
//            val response = apiServices?.getCities(page = nextPage)
//
//            return LoadResult.Page(
//                data = response?.body()!!.result!!,
//                prevKey = if (nextPage == 1) null else nextPage - 1,
//                nextKey = response.body()!!.page!! + 1
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
//        TODO("Not yet implemented")
//    }
//}