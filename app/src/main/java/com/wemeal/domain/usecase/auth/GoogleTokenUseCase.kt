package com.wemeal.domain.usecase.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.wemeal.domain.repository.auth.GoogleTokenRepository

class GoogleTokenUseCase(private val googleTokenRepository: GoogleTokenRepository) {

    suspend fun execute(authServerCode: String?) : GoogleTokenResponse = googleTokenRepository.create(authServerCode)
}