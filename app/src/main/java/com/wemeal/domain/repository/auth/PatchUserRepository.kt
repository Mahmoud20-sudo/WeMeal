package com.wemeal.domain.repository.auth

import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel

interface PatchUserRepository {
    suspend fun updateUser(): Resource<SuccessModel>
}