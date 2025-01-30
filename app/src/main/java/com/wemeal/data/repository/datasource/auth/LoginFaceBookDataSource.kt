package com.wemeal.data.repository.datasource.auth

import com.wemeal.data.model.user.UserModel
import retrofit2.Response

interface LoginFaceBookDataSource {
    suspend fun loginFB(accessToken: String): Response<UserModel>
}