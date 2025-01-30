package com.wemeal.domain.usecase.auth

import com.wemeal.domain.repository.auth.PatchUserRepository

class PatchUserUseCase(private val patchUserRepository: PatchUserRepository) {
    suspend fun execute() = patchUserRepository.updateUser()
}