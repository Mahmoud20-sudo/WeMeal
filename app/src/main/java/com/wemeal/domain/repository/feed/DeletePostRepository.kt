package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.delete.DeleteResponseModel
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel

interface DeletePostRepository {
    suspend fun deletePost(id: String): Resource<DeleteResponseModel>
}