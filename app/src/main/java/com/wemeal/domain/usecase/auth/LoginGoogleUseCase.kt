package com.wemeal.domain.usecase.auth

import android.content.Intent
import com.wemeal.domain.repository.auth.LoginRepository

class LoginGoogleUseCase(private val loginRepository: LoginRepository) {

    fun execute() : Intent = loginRepository.firebaseSignInWithGoogle()
}