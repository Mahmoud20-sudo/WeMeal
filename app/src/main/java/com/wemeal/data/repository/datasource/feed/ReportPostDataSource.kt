package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import com.wemeal.data.model.main.feed.actions.report.ReportResponseModel
import retrofit2.Response

interface ReportPostDataSource {
    suspend fun report(id: String, reason: String): Response<ReportResponseModel>
}