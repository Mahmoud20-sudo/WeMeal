package com.wemeal.presentation.util

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.wemeal.domain.repository.auth.GoogleTokenRepository
import com.wemeal.domain.repository.auth.PeopleResponseRepository
import com.wemeal.presentation.util.interfaces.ProfileFetchingListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

//TODO() this class will be a viewmodel soon
class GooglePlugin(
    private val googleCredential: GoogleCredential,
    private val googleTokenRepository: GoogleTokenRepository,
    private val peopleResponseRepository: PeopleResponseRepository
) : ProfileFetchingListener {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: GooglePlugin? = null

        fun getInstance(
            googleCredential: GoogleCredential,
            googleTokenRepository: GoogleTokenRepository,
            peopleResponseRepository: PeopleResponseRepository
        ): GooglePlugin {
            return when {
                instance != null -> instance!!
                else -> synchronized(this) {
                    if (instance == null) instance =
                        GooglePlugin(
                            googleCredential,
                            googleTokenRepository,
                            peopleResponseRepository
                        )
                    instance!!
                }
            }
        }
    }

    private fun getUserProfile(googleSignInAccount: GoogleSignInAccount) {
        CoroutineScope(Main).launch {
            val tokenResponse = googleTokenRepository.create(googleSignInAccount.serverAuthCode)
            googleCredential.setFromTokenResponse(tokenResponse)
            //val connections = peopleResponseRepository.create(googleCredential)
            //Log.v("Contacts list SIZE", connections.size.toString())
        }
    }

    override fun create(googleSignInAccount: GoogleSignInAccount) {
        getUserProfile(googleSignInAccount = googleSignInAccount)
    }
}