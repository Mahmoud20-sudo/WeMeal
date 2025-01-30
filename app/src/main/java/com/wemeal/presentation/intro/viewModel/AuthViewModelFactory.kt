package com.wemeal.presentation.intro.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.wemeal.domain.usecase.auth.*
import com.wemeal.domain.usecase.onboarding.GetResultUseCase

class AuthViewModelFactory(
    val application: Application,
    private val loginGoogleUseCase: LoginGoogleUseCase,
    private val loginFacebookUseCase: LoginFacebookUseCase,
    private val handleActivityResultUseCase: HandleActivityResultUseCase,
    private val getResultUseCase: GetResultUseCase,
    private val loginFacebookApiUserCase: LoginFacebookApiUserCase,
    private val loginGoogleApiUserCase: LoginGoogleApiUserCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java))
            return AuthViewModel(
                application,
                loginGoogleUseCase,
                loginFacebookUseCase,
                handleActivityResultUseCase,
                getResultUseCase,
                loginFacebookApiUserCase,
                loginGoogleApiUserCase
            ) as T

        throw IllegalArgumentException("Unknown view model")
    }

}