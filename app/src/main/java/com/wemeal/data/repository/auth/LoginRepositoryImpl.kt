package com.wemeal.data.repository.auth

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.appevents.AppEventsConstants
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.api.services.people.v1.PeopleServiceScopes
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*
import com.wemeal.data.model.AuthType
import com.wemeal.data.model.AuthUiModel
import com.wemeal.data.model.MaterialDialogContent
import com.wemeal.data.model.Result
import com.wemeal.domain.repository.auth.LoginRepository
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.util.Event
import com.wemeal.presentation.util.RC_GOOGLE_SIGN_IN_CODE
import com.wemeal.presentation.util.events.*
import com.wemeal.presentation.util.facebook_permissions
import com.wemeal.presentation.util.interfaces.ProfileFetchingListener
import com.wemeal.presentation.util.safeApiCall
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
class LoginRepositoryImpl @Inject constructor(
    context: Context,
    loginManager: LoginManager,
    @ActivityContext private val callbackManager: CallbackManager,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val profileFetchingListener: ProfileFetchingListener

) : LoginRepository {

    private val mContext: WeakReference<Context> = WeakReference(context)
    private val loginManager: WeakReference<LoginManager> = WeakReference(loginManager)

    private var _uiState: MutableLiveData<AuthUiModel> = MutableLiveData()
    var accessToken = ""

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: LoginRepositoryImpl? = null

        fun getInstance(
            context: Context,
            loginManager: LoginManager,
            callbackManager: CallbackManager,
            firebaseAuth: FirebaseAuth,
            googleSignInClient: GoogleSignInClient,
            profileFetchingListener: ProfileFetchingListener
        ): LoginRepositoryImpl {
            return when {
                instance != null && firebaseAuth.currentUser != null -> instance!!
                else -> synchronized(this) {
                    if (instance == null) instance =
                        LoginRepositoryImpl(
                            context,
                            loginManager,
                            callbackManager,
                            firebaseAuth,
                            googleSignInClient,
                            profileFetchingListener
                        )
                    instance!!
                }
            }
        }
    }

    override fun getResultLiveData(): MutableLiveData<AuthUiModel> {
        return _uiState
    }

    override fun firebaseSignInWithFacebook() {
        mContext.get().activity()
            ?.logEvent(CustomEvent.USER_CONTINUE_WITH_FACEBOOK, EventCase.ATTEMPT)
        loginManager.get()?.logInWithReadPermissions(
            mContext.get().activity(),
            facebook_permissions
        )
    }

    override fun firebaseSignInWithGoogle(): Intent {
        mContext.get().activity()
            ?.logEvent(CustomEvent.USER_CONTINUE_WITH_GOOGLE, EventCase.ATTEMPT)
        return googleSignInClient.signInIntent
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_GOOGLE_SIGN_IN_CODE && data != null) {
            handleGoogleSignInResult(data)
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private val mFacebookCallback = object : FacebookCallback<LoginResult> {

        override fun onSuccess(result: LoginResult?) {
            handleFacebookCredential(result?.accessToken?.token!!)
        }

        override fun onCancel() {
            mContext.get().activity()
                ?.logEvent(CustomEvent.USER_CONTINUE_WITH_FACEBOOK, EventCase.CANCEL)
//            CoroutineScope(IO).launch {
//                emitUiState(
//                    error = Event(
//                        AuthType.FACEBOOK to MaterialDialogContent(
//                            R.string.try_again, R.string.operation_cancelled_content,
//                            R.string.operation_cancelled, R.string.cancel
//                        )
//                    )
//                )
//            }
        }

        override fun onError(error: FacebookException?) {
            CoroutineScope(IO).launch {
                sendErrorState(AuthType.FACEBOOK)
            }
        }
    }

//    private val fbCallback = FacebookCallBackManager(mContext.get()!!, handleFacebookCredential = { credential ->
//        handleFacebookCredential(credential)
//    }, onCancel = {
//        mContext.get().activity()
//            ?.logEvent(CustomEvent.USER_CONTINUE_WITH_FACEBOOK, EventCase.CANCEL)
//        CoroutineScope(IO).launch {
//            emitUiState(
//                error = Event(
//                    AuthType.FACEBOOK to MaterialDialogContent(
//                        R.string.try_again, R.string.operation_cancelled_content,
//                        R.string.operation_cancelled, R.string.cancel
//                    )
//                )
//            )
//        }
//    }, onError = {
//        CoroutineScope(IO).launch {
//            sendErrorState(AuthType.FACEBOOK)
//        }
//    })

    private fun handleFacebookCredential(token: String) {
        CoroutineScope(Main).launch {
            emitUiState(showProgress = true)
            safeApiCall {
                Result.Success(
                    signInWithCredential(
                        FacebookAuthProvider.getCredential(
                            token
                        )
                    )!!
                )
            }.also {
                if (it is Result.Success && it.data.user != null) {
                    emitUiState(success = true, accessToken = token, authType = AuthType.GOOGLE)
                } else if (it is Result.Error) sendErrorState(AuthType.FACEBOOK, it)
            }
        }
    }

    @Throws(Exception::class)
    private suspend fun signInWithCredential(authCredential: AuthCredential): AuthResult? {
        return firebaseAuth.signInWithCredential(authCredential).await()
    }

    private suspend fun emitUiState(
        showProgress: Boolean = false,
        error: Event<Pair<AuthType, MaterialDialogContent>>? = null,
        success: Boolean = false,
        accessToken: String? = null,
        authType: AuthType? = null
    ) = withContext(Main)
    {
        AuthUiModel(showProgress, error, success, accessToken).also {
            _uiState.postValue(it)
            if (success) {
                when (authType) {
                    AuthType.FACEBOOK -> mContext.get().activity()?.logFacebookStandardEvent(
                        FacebookStandardEvent.EVENT_NAME_COMPLETED_REGISTRATION, EventCase.SUCCESS,
                        bundleOf(
                            AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD to LOGIN_METHOD_FACEBOOK,
                            AppEventsConstants.EVENT_PARAM_CURRENCY to CURRENCY_EGP,
                        ), 1.00
                    )
                    AuthType.GOOGLE ->  mContext.get().activity()?.logFirebaseStandardEvent(
                        FirebaseStandardEvent.LOGIN,
                        EventCase.SUCCESS,
                        bundleOf(FirebaseAnalytics.Param.METHOD to LOGIN_METHOD_GOOGLE)
                    ) //TODO need to check if this is facebook or google sign in
                }
            }
        }
    }

    private suspend fun sendErrorState(authType: AuthType, result: Result<AuthResult>? = null) {
        emitUiState(
            error = Event(
                authType to MaterialDialogContent("", "", "")
            )
        )
        when (authType) {
            AuthType.FACEBOOK -> mContext.get().activity()
                ?.logEvent(CustomEvent.USER_CONTINUE_WITH_FACEBOOK, EventCase.FAILURE)
            else -> {
                val case =
                    if ((result as Result.Error).exception.message?.contains("12501") == true)//12501 = user cancel login
                        EventCase.CANCEL else EventCase.FAILURE
                mContext.get().activity()?.logEvent(CustomEvent.USER_CONTINUE_WITH_GOOGLE, case)
            }
        }
    }

    private fun handleGoogleSignInResult(data: Intent) {
        CoroutineScope(IO).launch {
            emitUiState(showProgress = true)
            safeApiCall {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                val authResult =
                    signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))!!

                if (GoogleSignIn.hasPermissions(
                        account,
                        Scope(PeopleServiceScopes.CONTACTS_READONLY)
                    )
                ) {
//                    profileFetchingListener.create(account)
                } else {
                    TODO()//Log that permissions not given
                }
//                GoogleAdditionalDetailsTask(context).execute(account)
                profileFetchingListener.create(account)

                //GET ACCESS TOKEN
                kotlin.runCatching {
                    val scope: String = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE
                    accessToken = GoogleAuthUtil.getToken(
                        mContext.get()!!,
                        account.account!!, scope, Bundle()
                    )
                    //SharedPreferencesManager.instance.accessToken = accessToken
                    //Log.e("AA", accessToken)
                }

                Result.Success(authResult)
            }.also {
                if (it is Result.Success && it.data.user != null) {
                    emitUiState(
                        success = true,
                        accessToken = accessToken,
                        authType = AuthType.GOOGLE
                    )
                } else sendErrorState(AuthType.GOOGLE, it)
            }
        }
    }

    //constructor
    init {
        _uiState.value = AuthUiModel(false, null, false, accessToken = accessToken)
        loginManager.registerCallback(callbackManager, mFacebookCallback)
    }

}
//        emitUiState(
//            error = Event(
//                authType to MaterialDialogContent(
//                    R.string.try_again, R.string.internet_not_working,
//                    R.string.limited_internet_connection, R.string.cancel
//                )
//            )
//        )

