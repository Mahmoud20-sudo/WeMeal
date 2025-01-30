package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.feed.FeedModel

interface GetFeedRepository {
    suspend fun getFeed(afterId : String?): Resource<FeedModel>
}