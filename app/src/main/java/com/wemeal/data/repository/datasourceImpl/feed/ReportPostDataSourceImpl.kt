package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import com.wemeal.data.model.main.feed.actions.report.ReportRequestModel
import com.wemeal.data.model.main.feed.actions.report.ReportResponseModel
import com.wemeal.data.repository.datasource.feed.LikePostDataSource
import com.wemeal.data.repository.datasource.feed.ReportPostDataSource
import retrofit2.Response

class ReportPostDataSourceImpl(private val apiServices: ApiServices) : ReportPostDataSource {
    override suspend fun report(
        id: String,
        reason: String
    ): Response<ReportResponseModel> {
        return apiServices.reportPost(id = id, reportRequestModel = ReportRequestModel(reason))
    }
}
