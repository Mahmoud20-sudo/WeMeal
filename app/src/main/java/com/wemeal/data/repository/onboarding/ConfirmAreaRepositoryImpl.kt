package com.wemeal.data.repository.onboarding

import com.google.gson.JsonParseException
import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.repository.datasource.onboarding.ConfirmAreaDataSource
import com.wemeal.domain.repository.onboarding.ConfirmAreaRepository
import org.json.JSONObject
import retrofit2.Response

class ConfirmAreaRepositoryImpl(private val confirmAreaDataSource: ConfirmAreaDataSource) :
    ConfirmAreaRepository {
    override suspend fun confirmArea(
        areaModel: com.wemeal.data.model.onboarding.nearest.Result
    ): Resource<SuccessModel> {
        return responseToResource(
            confirmAreaDataSource.confirmArea(
                areaModel = areaModel
            )
        )
    }

    private fun responseToResource(response: Response<SuccessModel>): Resource<SuccessModel> {
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
        }catch (e: JsonParseException){
            e.message
        }
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
