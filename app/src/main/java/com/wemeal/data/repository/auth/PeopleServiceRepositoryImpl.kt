package com.wemeal.data.repository.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.people.v1.PeopleService
import com.wemeal.domain.repository.auth.PeopleServiceRepository

class PeopleServiceRepositoryImpl(
    private val httpTransport: HttpTransport,
    private val jacksonFactory: JacksonFactory
) : PeopleServiceRepository {
    override suspend fun create(googleCredential: GoogleCredential): PeopleService {
        return PeopleService.Builder(httpTransport, jacksonFactory, googleCredential)
            .setApplicationName("WeMeal")
            .build()
    }

}