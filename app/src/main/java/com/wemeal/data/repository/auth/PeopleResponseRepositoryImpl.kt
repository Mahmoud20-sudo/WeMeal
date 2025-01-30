package com.wemeal.data.repository.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.people.v1.model.Person
import com.wemeal.domain.repository.auth.PeopleResponseRepository
import com.wemeal.domain.repository.auth.PeopleServiceRepository
import com.wemeal.presentation.util.googleContactsRequiredFields
import com.wemeal.presentation.util.googlePath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PeopleResponseRepositoryImpl(private val peopleServiceRepository: PeopleServiceRepository) :
    PeopleResponseRepository {
    private var connections: MutableList<Person> = ArrayList()

    override suspend fun create(googleCredential: GoogleCredential): MutableList<Person> {
        return getPeople(googleCredential = googleCredential)
    }

    private suspend fun getPeople(googleCredential: GoogleCredential): MutableList<Person> {
        //Cash contacts to room database
//        withContext(CoroutineScope(IO).coroutineContext) {
//            val peopleService = peopleServiceRepository.create(googleCredential)
//            kotlin.runCatching {
//                val response = peopleService.people().connections()
//                    .list(googlePath)
//                    .setRequestMaskIncludeField(googleContactsRequiredFields)
//                    .execute()
//
//                connections = response.connections
////                for (person in connections) {
////                    if (!person.isEmpty()) {
////                        val names = person.names
////                        val emailAddresses = person.emailAddresses
////                        val phoneNumbers = person.phoneNumbers
////                        if (phoneNumbers != null) for (phoneNumber in phoneNumbers) Log.d(
////                            "TAG",
////                            "phone: " + phoneNumber.value
////                        )
////                        if (emailAddresses != null) for (emailAddress in emailAddresses) Log.d(
////                            "TAG",
////                            "email: " + emailAddress.value
////                        )
////                    }
////                }
//                connections
//                TODO()//Cash contacts to room database
//            }
//        }
        return connections
    }

}