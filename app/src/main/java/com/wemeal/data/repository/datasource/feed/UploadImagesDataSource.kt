package com.wemeal.data.repository.datasource.feed

import com.wemeal.data.model.main.create.AwsResponseModel
import retrofit2.Response
import java.io.File

interface UploadImagesDataSource {
    suspend fun upload(file: File): Response<AwsResponseModel>
}