package com.wemeal.presentation.util

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.wemeal.presentation.BaseActivity

class SocialAuth(
    val loginManager: LoginManager,
    val manager: CallbackManager,
    val firebaseAuth: FirebaseAuth,
    val activity: BaseActivity
) {

//    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_API)
//        .requestEmail()
//        .build()
//    private val mGoogleSignClient by lazy {
//        GoogleSignIn.getClient(activity, gso)
//    }
//
//    private val _uiState = MutableLiveData<AuthUiModel>()
//
//    fun googleSignIn() = mGoogleSignClient.signInIntent
//
//    private val facebook_permissions =
//        mutableListOf("email", "public_profile", "user_friends", "user_gender")
//
//    companion object {
//        @Volatile
//        private var INSTANCE: SocialAuth? = null
//
//        @Synchronized
//        fun getInstance(
//            loginManager: LoginManager,
//            manager: CallbackManager,
//            firebaseAuth: FirebaseAuth,
//            activity: BaseActivity
//        ): SocialAuth =
//            INSTANCE ?: SocialAuth(loginManager, manager, firebaseAuth, activity).also { INSTANCE = it }
//    }
//
//    private val mFacebookCallback = object : FacebookCallback<LoginResult> {
//        override fun onSuccess(result: LoginResult?) {
//            val credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
//            handleFacebookCredential(credential)
//        }
//
//        override fun onCancel() {
//            CoroutineScope(Dispatchers.IO).launch {
//                emitUiState(
//                    error = Event(
//                        AuthType.FACEBOOK to MaterialDialogContent(
//                            R.string.try_again, R.string.operation_cancelled_content,
//                            R.string.operation_cancelled, R.string.cancel
//                        )
//                    )
//                )
//            }
//        }
//
//        override fun onError(error: FacebookException?) {
//            CoroutineScope(Dispatchers.IO).launch {
//                sendErrorState(AuthType.FACEBOOK)
//            }
//        }
//    }
//
//    private fun handleFacebookCredential(authCredential: AuthCredential) {
//        CoroutineScope(Dispatchers.IO).launch {
//            emitUiState(showProgress = true)
//            safeApiCall { Result.Success(signInWithCredential(authCredential)!!) }.also {
//                if (it is Result.Success && it.data.user != null) emitUiState(success = true)
//                else if (it is Result.Error) handleErrorStateForSignInCredential(
//                    it.exception, AuthType.FACEBOOK
//                )
//            }
//        }
//    }
//
//    private suspend fun sendErrorState(authType: AuthType) {
//        emitUiState(
//            error = Event(
//                authType to MaterialDialogContent(
//                    R.string.try_again, R.string.internet_not_working,
//                    R.string.limited_internet_connection, R.string.cancel
//                )
//            )
//        )
//    }
//    //////////////////////// Firebase Authentication Common Code Starts ////////////////////////////
//
//    private suspend fun handleErrorStateForSignInCredential(
//        exception: Exception, authType: AuthType
//    ) {
//        if (exception is FirebaseAuthUserCollisionException) {
//            sendErrorState(authType)
//        } else sendErrorState(authType)
//    }
//
//    fun loginByFacebook() : MutableLiveData<AuthUiModel> {
//        loginManager.registerCallback(manager, mFacebookCallback)
//        loginManager.logInWithReadPermissions(activity, facebook_permissions)
//
//        return _uiState
//    }
//
//    private suspend fun emitUiState(
//        showProgress: Boolean = false,
//        error: Event<Pair<AuthType, MaterialDialogContent>>? = null,
//        success: Boolean = false,
//        linkProvider: Event<Pair<List<String>, MaterialDialogContent>>? = null
//    ) = withContext(Dispatchers.Main)
//    {
//        AuthUiModel(showProgress, error, success, linkProvider).also {
//            _uiState.value = it
//        }
//    }
//
//    @Throws(Exception::class)
//    private suspend fun signInWithCredential(authCredential: AuthCredential): AuthResult? {
//        return firebaseAuth.signInWithCredential(authCredential).await()
//    }
//
//    fun handleGoogleSignInResult(data: Intent) {
//        CoroutineScope(Dispatchers.IO).launch {
//            emitUiState(showProgress = true)
//            safeApiCall {
//                val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
//                val authResult =
//                    signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))!!
//                Result.Success(authResult)
//            }.also {
//                if (it is Result.Success && it.data.user != null)
//                    emitUiState(success = true)
//                else sendErrorState(AuthType.GOOGLE)
//            }
//        }
//    }


}