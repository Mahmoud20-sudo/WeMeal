package com.wemeal.domain.usecase.auth

import com.wemeal.domain.repository.auth.LoginRepository

class LoginFacebookUseCase(private val loginRepository: LoginRepository) {

    fun execute() = loginRepository.firebaseSignInWithFacebook()

}