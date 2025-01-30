package com.wemeal.data.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import com.wemeal.data.model.main.feed.actions.report.ReportResponseModel
import com.wemeal.data.repository.datasource.feed.LikePostDataSource
import com.wemeal.data.repository.datasource.feed.ReportPostDataSource
import com.wemeal.domain.repository.feed.LikePostRepository
import com.wemeal.domain.repository.feed.ReportPostRepository
import org.json.JSONObject
import retrofit2.Response

class ReportPostRepositoryImpl(private val reportPostDataSource: ReportPostDataSource) :
    ReportPostRepository {
    override suspend fun report(
        id: String,
        reason :String
    ): Resource<ReportResponseModel> {
        return responseToResource(
            reportPostDataSource.report(id = id, reason = reason)
        )
    }

    private fun responseToResource(response: Response<ReportResponseModel>): Resource<ReportResponseModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return when(result.result.error != null){
                    true -> Resource.Error(
                        when {
                            result.result?.error?.en?.contains("not found") == false -> result.result?.error?.en
                            else -> "removed"
                        }
                    )
                    false -> Resource.Success(result)
                }
            }
        }
        return Resource.Error(parseErrorMessage(response.errorBody()?.string()))
    }

    private fun parseErrorMessage(error: String?): String? {
        return try {
            val json = JSONObject(error ?: "")
            json.getString("type")
        } catch (e: Exception) {
            e.message
        }
    }
}