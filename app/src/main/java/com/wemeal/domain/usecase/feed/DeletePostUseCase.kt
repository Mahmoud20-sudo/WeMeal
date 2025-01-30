package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.DeletePostRepository

class DeletePostUseCase(private val deletePostRepository: DeletePostRepository) {
    suspend fun execute(id: String) = deletePostRepository.deletePost(id = id)
}