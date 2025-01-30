package com.wemeal.data.repository.datasourceImpl.gallery

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.main.gallery.BrandGalleryModel
import com.wemeal.data.repository.datasource.gallery.GetBrandGalleryDataSource
import retrofit2.Response

class GetBrandGalleryDataSourceImpl(private val apiServices: ApiServices) :
    GetBrandGalleryDataSource {
    override suspend fun getBrandGallery(
        placeId: String, objectId: String?, objectType: String?
    ): Response<BrandGalleryModel> {
        return when (objectId) {
            null -> apiServices.getBrandGallery(placeId = placeId)
            else -> apiServices.getGallery(
                placeId = placeId,
                objectId = objectId,
                objectType = objectType
            )
        }

    }
}
