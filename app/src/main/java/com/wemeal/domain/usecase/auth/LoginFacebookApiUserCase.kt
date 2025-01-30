package com.wemeal.domain.usecase.auth

import com.wemeal.domain.repository.auth.LoginFacebookRepository

class LoginFacebookApiUserCase(private val loginFacebookRepository: LoginFacebookRepository) {
    suspend fun execute(accessToken: String) = loginFacebookRepository.loginFacebook(accessToken = accessToken)
}