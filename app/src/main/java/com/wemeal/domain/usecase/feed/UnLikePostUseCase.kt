package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.UnLikePostRepository

class UnLikePostUseCase(private val unLikePostRepository: UnLikePostRepository) {
    suspend fun execute(id: String) = unLikePostRepository.unLikePost(id = id)
}