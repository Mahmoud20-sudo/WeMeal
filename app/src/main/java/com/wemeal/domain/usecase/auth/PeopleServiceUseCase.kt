package com.wemeal.domain.usecase.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.people.v1.PeopleService
import com.wemeal.domain.repository.auth.PeopleServiceRepository

class PeopleServiceUseCase(private val peopleServiceRepository: PeopleServiceRepository) {

    suspend fun execute(googleCredential: GoogleCredential): PeopleService = peopleServiceRepository.create(googleCredential = googleCredential)
}