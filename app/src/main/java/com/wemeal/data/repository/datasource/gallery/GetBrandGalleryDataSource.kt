package com.wemeal.data.repository.datasource.gallery

import com.wemeal.data.model.main.gallery.BrandGalleryModel
import retrofit2.Response

interface GetBrandGalleryDataSource {
    suspend fun getBrandGallery(
        placeId: String,
        objectId: String?,
        objectType: String?
    ): Response<BrandGalleryModel>
}