package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel

interface LikePostRepository {
    suspend fun likePost(id: String): Resource<LikeResponseModel>
}