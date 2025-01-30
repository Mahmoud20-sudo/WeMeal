package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.LikePostRepository

class LikePostUseCase(private val likePostRepository: LikePostRepository) {
    suspend fun execute(id: String) = likePostRepository.likePost(id = id)
}