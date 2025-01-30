package com.wemeal.data.api

import com.wemeal.data.model.main.create.AwsResponseModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ImageApiService {
    @POST(value = "image/")
    suspend fun uploadImg(
        @Query("model") model: String,
        @Query("unique-name") uniqueName: String,
        @Query("picture-type") pictureType: String,
        @Body image: RequestBody
    ): Response<AwsResponseModel>
}