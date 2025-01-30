package com.wemeal.domain.usecase.auth

import com.wemeal.domain.repository.auth.LoginGoogleRepository

class LoginGoogleApiUserCase(private val loginGoogleRepository: LoginGoogleRepository) {
    suspend fun execute(accessToken: String) = loginGoogleRepository.loginGoogle(accessToken = accessToken)
}