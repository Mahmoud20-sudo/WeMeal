package com.wemeal.data.repository.datasourceImpl.auth

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.params.AccessTokenModel
import com.wemeal.data.model.user.UserModel
import com.wemeal.data.repository.datasource.auth.LoginGoogleDataSource
import retrofit2.Response

class LoginGoogleDataSourceImpl(private val apiServices: ApiServices) : LoginGoogleDataSource {
    override suspend fun loginGoogle(accessToken: String): Response<UserModel> {
        return apiServices.loginGoogle(accessTokenModel = setRequestParams(accessToken))
    }

    private fun setRequestParams(accessToken: String): AccessTokenModel {
        return AccessTokenModel(access_token = accessToken)
    }
}