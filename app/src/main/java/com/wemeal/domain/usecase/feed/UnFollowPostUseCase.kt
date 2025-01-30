package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.UnFollowPostRepository

class UnFollowPostUseCase(private val unFollowPostRepository: UnFollowPostRepository) {
    suspend fun execute(id: String) = unFollowPostRepository.unfollowPost(id = id)
}