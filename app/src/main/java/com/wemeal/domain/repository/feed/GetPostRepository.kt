package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.FeedDetailsModel
import com.wemeal.data.model.main.feed.Result

interface GetPostRepository {
    suspend fun getPost(id: String): Resource<FeedDetailsModel>
}