package com.wemeal.domain.repository.auth

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.wemeal.data.model.AuthUiModel

interface LoginRepository {

    fun getResultLiveData() : MutableLiveData<AuthUiModel>

    fun firebaseSignInWithFacebook()

    fun firebaseSignInWithGoogle(): Intent

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}