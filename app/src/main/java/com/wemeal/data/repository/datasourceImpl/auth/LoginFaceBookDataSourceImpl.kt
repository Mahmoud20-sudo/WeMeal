package com.wemeal.data.repository.datasourceImpl.auth

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.params.AccessTokenModel
import com.wemeal.data.model.user.UserModel
import com.wemeal.data.repository.datasource.auth.LoginFaceBookDataSource
import retrofit2.Response

class LoginFaceBookDataSourceImpl(private val apiServices: ApiServices) : LoginFaceBookDataSource {
    override suspend fun loginFB(accessToken: String): Response<UserModel> {
        return apiServices.loginFB(accessTokenModel = setRequestParams(accessToken))
    }

    private fun setRequestParams(accessToken: String): AccessTokenModel {
        return AccessTokenModel(access_token = accessToken)
    }
}