package com.wemeal.data.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.repository.datasource.onboarding.GetSubAreasDataSource
import com.wemeal.domain.repository.onboarding.GetSubAreasRepository
import retrofit2.Response

class GetSubAreasRepositoryImpl(private val getSubAreasDataSource: GetSubAreasDataSource) :
    GetSubAreasRepository {
    override suspend fun getSubAreas(
        areaId: String,
        searchText: String,
        page: Int
    ): Resource<CountriesModel> {
        return responseToResource(
            getSubAreasDataSource.getSubAreas(
                areaId = areaId,
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
