package com.wemeal.domain.repository.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.services.people.v1.PeopleService
import com.google.api.services.people.v1.model.Person
import kotlinx.coroutines.flow.Flow

interface PeopleResponseRepository {
   suspend fun create(googleCredential: GoogleCredential) : MutableList<Person>
}