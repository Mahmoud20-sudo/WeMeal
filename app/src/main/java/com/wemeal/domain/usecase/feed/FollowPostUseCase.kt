package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.FollowPostRepository

class FollowPostUseCase(private val followPostRepository: FollowPostRepository) {
    suspend fun execute(id: String) = followPostRepository.followPost(id = id)
}