package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.report.ReportResponseModel

interface ReportPostRepository {
    suspend fun report(
        id: String,
        reason : String
    ): Resource<ReportResponseModel>
}