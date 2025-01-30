package com.wemeal.presentation.intro.viewModel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.wemeal.data.model.AuthUiModel
import com.wemeal.data.model.Resource
import com.wemeal.data.model.user.UserModel
import com.wemeal.domain.usecase.auth.*
import com.wemeal.domain.usecase.onboarding.GetResultUseCase
import kotlinx.coroutines.launch


class AuthViewModel(
    application: Application,
    private val loginGoogleUseCase: LoginGoogleUseCase,
    private val loginFacebookUseCase: LoginFacebookUseCase,
    private val handleActivityResultUseCase: HandleActivityResultUseCase,
    private val getResultUseCase: GetResultUseCase,
    private val loginFacebookApiUserCase: LoginFacebookApiUserCase,
    private val loginGoogleApiUserCase: LoginGoogleApiUserCase
) : AndroidViewModel(application) {

    val uiState: LiveData<AuthUiModel> get() = _uiState

    private var _uiState: MutableLiveData<AuthUiModel> = getResultUseCase.execute()
    var userLiveData: MutableLiveData<Resource<UserModel>> = MutableLiveData()

    fun googleSignIn() = loginGoogleUseCase.execute()

    fun facebookSignIn() = loginFacebookUseCase.execute()

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        handleActivityResultUseCase.execute(requestCode, resultCode, data)
    }

    fun loginRemote(providerId: String, accessToken: String?) {
        viewModelScope.launch {
            when {
                providerId.contains("facebook") -> {//login by fb
                    val userResponse =
                        loginFacebookApiUserCase.execute(accessToken!!)
                    userLiveData.postValue(userResponse)
                }
                else -> {//login by google
                    val userResponse =
                        loginGoogleApiUserCase.execute(accessToken!!)
                    userLiveData.postValue(userResponse)
                }
            }
        }
    }
}