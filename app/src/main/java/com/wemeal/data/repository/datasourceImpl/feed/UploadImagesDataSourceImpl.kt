package com.wemeal.data.repository.datasourceImpl.feed

import com.wemeal.data.api.ImageApiService
import com.wemeal.data.model.main.create.AwsResponseModel
import com.wemeal.data.repository.datasource.feed.UploadImagesDataSource
import retrofit2.Response
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.util.*

class UploadImagesDataSourceImpl(private val imageApiService: ImageApiService) :
    UploadImagesDataSource {
    override suspend fun upload(file: File): Response<AwsResponseModel> {
//        val requestFile = "application/octet".toMediaTypeOrNull()
//        val body = RequestBody.create(
//            "image/png".toMediaTypeOrNull(),
//            Files.readAllBytes(file.toPath()))

        //AWS not accepting JPG extension
        //val extension: String = MimeTypeMap.getFileExtensionFromUrl(file.toString())

        val filName =
            if (isProbablyArabic(file.name)) "IMG_${
                Random().nextInt()
            }.webp" else file.name

        return imageApiService.uploadImg(
            model = "posts",
            uniqueName = filName,
            pictureType = "gallery",
            image = file.asRequestBody("image/webp".toMediaTypeOrNull())
        )
    }
}

private fun isProbablyArabic(s: String): Boolean {
    var i = 0
    while (i < s.length) {
        val c = s.codePointAt(i)
        if (c in 0x0600..0x06E0) return true
        i += Character.charCount(c)
    }
    return false
}
