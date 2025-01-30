package com.wemeal.domain.repository.auth

import com.wemeal.data.model.Resource
import com.wemeal.data.model.user.UserModel

interface LoginFacebookRepository {
    suspend fun loginFacebook(accessToken: String): Resource<UserModel>
}