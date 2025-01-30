package com.wemeal.domain.repository.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.services.people.v1.PeopleService

interface PeopleServiceRepository {
   suspend fun create(googleCredential: GoogleCredential) : PeopleService
}