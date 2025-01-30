package com.wemeal.data.repository.auth

import com.wemeal.data.model.Resource
import com.wemeal.data.model.user.UserModel
import com.wemeal.data.repository.datasource.auth.LoginGoogleDataSource
import com.wemeal.domain.repository.auth.LoginGoogleRepository
import retrofit2.Response

class LoginGoogleRepositoryImpl(private val loginGoogleDataSource: LoginGoogleDataSource) :
    LoginGoogleRepository {
    override suspend fun loginGoogle(accessToken: String): Resource<UserModel> {
        return responseToResource(loginGoogleDataSource.loginGoogle(accessToken = accessToken))
    }

    private fun responseToResource(response: Response<UserModel>): Resource<UserModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}