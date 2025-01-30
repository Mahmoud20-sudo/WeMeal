package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.ReportPostRepository

class ReportPostUseCase(private val reportPostRepository: ReportPostRepository) {
    suspend fun execute(
        id: String,
        reason : String
    ) = reportPostRepository.report(
        id = id,
        reason = reason
    )
}