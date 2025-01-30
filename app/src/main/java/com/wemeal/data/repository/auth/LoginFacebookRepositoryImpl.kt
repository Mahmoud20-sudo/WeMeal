package com.wemeal.data.repository.auth

import com.wemeal.data.model.Resource
import com.wemeal.data.model.user.UserModel
import com.wemeal.data.repository.datasource.auth.LoginFaceBookDataSource
import com.wemeal.domain.repository.auth.LoginFacebookRepository
import retrofit2.Response

class LoginFacebookRepositoryImpl(private val loginFaceBookDataSource: LoginFaceBookDataSource) :
    LoginFacebookRepository {
    override suspend fun loginFacebook(accessToken: String): Resource<UserModel> {
        return responseToResource(loginFaceBookDataSource.loginFB(accessToken = accessToken))
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