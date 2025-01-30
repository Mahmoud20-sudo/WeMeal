package com.wemeal.data.repository.datasource.auth

import com.wemeal.data.model.user.UserModel
import retrofit2.Response

interface LoginGoogleDataSource {
    suspend fun loginGoogle(accessToken: String): Response<UserModel>
}