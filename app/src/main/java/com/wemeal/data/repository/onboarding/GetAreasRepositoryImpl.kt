package com.wemeal.data.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.repository.datasource.onboarding.GetAreasDataSource
import com.wemeal.domain.repository.onboarding.GetAreasRepository
import retrofit2.Response

class GetAreasRepositoryImpl(private val getAreasDataSource: GetAreasDataSource) :
    GetAreasRepository {
    override suspend fun getAreas(
        cityId: String,
        searchText: String,
        page: Int
    ): Resource<CountriesModel> {
        return responseToResource(
            getAreasDataSource.getAreas(
                cityId = cityId,
                page = page,
                searchText = searchText
            )
        )
    }

    private fun responseToResource(response: Response<CountriesModel>): Resource<CountriesModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}

//class GetCitiesRepositoryImpl constructor(
//    private val getCitiesDataSource: GetCitiesDataSource?
//) : PagingSource<Int, Result>(), GetCitiesRepository {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
//        return try {
//
//            Log.e("AAa", params.toString())
//            val nextPage = params.key ?: 1
//            val movieListResponse = getCitiesDataSource?.getCities(nextPage)
//
//            LoadResult.Page(
//                data = movieListResponse?.body()?.result!!,
//                prevKey = if (nextPage == 1) null else nextPage - 1,
//                nextKey = if(nextPage == 1) 2 else null
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
//        return null
//    }
//
//    override suspend fun getCities(params: LoadParams<Int>): LoadResult<Int, Result>{
//        TODO("Not yet implemented")
//    }
//}
