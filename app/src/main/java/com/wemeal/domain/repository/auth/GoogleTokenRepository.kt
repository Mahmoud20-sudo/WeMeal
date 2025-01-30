package com.wemeal.domain.repository.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse

interface GoogleTokenRepository {
   suspend fun create(authServerCode: String?) : GoogleTokenResponse
}