package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel

interface FollowPostRepository {
    suspend fun followPost(id: String): Resource<SuccessModel>
}