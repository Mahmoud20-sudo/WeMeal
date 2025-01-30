package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.GetFeedRepository


class GetFeedUseCase(private val getFeedRepository: GetFeedRepository) {
    suspend fun execute(afterId: String?) = getFeedRepository.getFeed(afterId = afterId)
}