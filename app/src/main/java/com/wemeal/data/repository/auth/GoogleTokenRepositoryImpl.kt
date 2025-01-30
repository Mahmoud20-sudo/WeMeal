package com.wemeal.data.repository.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.wemeal.BuildConfig
import com.wemeal.domain.repository.auth.GoogleTokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleTokenRepositoryImpl(
    private val httpTransport: HttpTransport,
    private val jacksonFactory: JacksonFactory
) : GoogleTokenRepository {

    lateinit var tokenResponse: GoogleTokenResponse
    override suspend fun create(authServerCode: String?): GoogleTokenResponse {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            runCatching {
                tokenResponse = GoogleAuthorizationCodeTokenRequest(
                    httpTransport,
                    jacksonFactory,
                    BuildConfig.GOOGLE_WEB_CLIENT_API,
                    BuildConfig.GOOGLE_WEB_SECRET,
                    authServerCode,
                    BuildConfig.REDIRECT_URL
                ).execute()
            }
        }
        return tokenResponse
    }
}