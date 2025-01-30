package com.wemeal.domain.repository.auth

import com.wemeal.data.model.Resource
import com.wemeal.data.model.user.UserModel

interface LoginGoogleRepository {
    suspend fun loginGoogle(accessToken: String): Resource<UserModel>
}