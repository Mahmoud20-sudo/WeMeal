package com.wemeal.domain.repository.feed

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.create.AwsResponseModel
import java.io.File

interface UploadImagesRepository {
    suspend fun upload(file: File): Resource<AwsResponseModel>
}