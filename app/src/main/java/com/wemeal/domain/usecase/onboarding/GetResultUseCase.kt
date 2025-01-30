package com.wemeal.domain.usecase.onboarding

import androidx.lifecycle.MutableLiveData
import com.wemeal.data.model.AuthUiModel
import com.wemeal.domain.repository.auth.LoginRepository

class GetResultUseCase(private val loginRepository: LoginRepository) {

    fun execute(): MutableLiveData<AuthUiModel> {
        return loginRepository.getResultLiveData()
    }
}