package com.wemeal.domain.usecase.gallery

import com.wemeal.domain.repository.gallery.GetBrandGalleryRepository

class GetBrandGalleryUseCase(private val getBrandGalleryRepository: GetBrandGalleryRepository) {
    suspend fun execute(placeId: String, objectId: String?, objectType: String?) =
        getBrandGalleryRepository.getGallery(
            placeId = placeId,
            objectId = objectId,
            objectType = objectType
        )
}