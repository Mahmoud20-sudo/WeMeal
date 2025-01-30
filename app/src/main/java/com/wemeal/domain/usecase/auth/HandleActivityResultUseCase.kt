package com.wemeal.domain.usecase.auth

import android.content.Intent
import com.wemeal.domain.repository.auth.LoginRepository

class HandleActivityResultUseCase(private val loginRepository: LoginRepository) {

    fun execute(requestCode: Int, resultCode: Int, data: Intent?) =
        loginRepository.handleOnActivityResult(requestCode, resultCode, data)
}