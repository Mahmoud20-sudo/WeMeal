package com.wemeal.domain.usecase.feed

import com.wemeal.domain.repository.feed.UploadImagesRepository
import java.io.File

class UploadImagesUseCase(private val uploadImagesRepository: UploadImagesRepository) {
    suspend fun execute(file: File) = uploadImagesRepository.upload(file = file)
}