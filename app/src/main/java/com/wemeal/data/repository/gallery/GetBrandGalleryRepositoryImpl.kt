package com.wemeal.data.repository.gallery

import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.gallery.BrandGalleryModel
import com.wemeal.data.repository.datasource.gallery.GetBrandGalleryDataSource
import com.wemeal.domain.repository.gallery.GetBrandGalleryRepository
import org.json.JSONObject
import retrofit2.Response

class GetBrandGalleryRepositoryImpl(private val getBrandGalleryDataSource: GetBrandGalleryDataSource) :
    GetBrandGalleryRepository {
    override suspend fun getGallery(
        placeId: String,
        objectId: String?,
        objectType: String?
    ): Resource<BrandGalleryModel> {
        return responseToResource(
            getBrandGalleryDataSource.getBrandGallery(
                placeId = placeId,
                objectId = objectId,
                objectType = objectType
            )
        )
    }

    private fun responseToResource(response: Response<BrandGalleryModel>): Resource<BrandGalleryModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(parseErrorMessage(response.errorBody()?.string()))
    }

    private fun parseErrorMessage(error: String?): String? {
        return try {
            val json = JSONObject(error ?: "")
            json.getString("type")
        } catch (e: Exception) {
            e.message
        }
    }
}