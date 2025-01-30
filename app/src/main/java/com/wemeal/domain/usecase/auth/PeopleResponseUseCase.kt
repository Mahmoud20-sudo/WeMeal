package com.wemeal.domain.usecase.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.people.v1.model.Person
import com.wemeal.domain.repository.auth.PeopleResponseRepository

class PeopleResponseUseCase(private val peopleResponseRepository: PeopleResponseRepository) {

    suspend fun execute(googleCredential: GoogleCredential) : MutableList<Person> = peopleResponseRepository.create(googleCredential)
}