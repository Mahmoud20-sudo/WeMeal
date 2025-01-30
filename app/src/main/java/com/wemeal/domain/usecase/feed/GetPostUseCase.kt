package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.GetPostRepository

class GetPostUseCase(private val getPostRepository: GetPostRepository) {
    suspend fun execute(id: String) = getPostRepository.getPost(id = id)
}