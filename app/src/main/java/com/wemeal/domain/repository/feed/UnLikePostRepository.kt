package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.actions.unlike.UnLikeResponseModel

interface UnLikePostRepository {
    suspend fun unLikePost(id: String): Resource<UnLikeResponseModel>
}