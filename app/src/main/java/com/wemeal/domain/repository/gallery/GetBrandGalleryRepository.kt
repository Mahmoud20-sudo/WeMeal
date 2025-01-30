package com.wemeal.domain.repository.gallery

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.gallery.BrandGalleryModel

interface GetBrandGalleryRepository {
    suspend fun getGallery(
        placeId: String,
        objectId: String?,
        objectType: String?
    ): Resource<BrandGalleryModel>
}