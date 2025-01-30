package com.wemeal.data.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.suggested_brands.BrandsModel
import com.wemeal.data.repository.datasource.onboarding.GetSuggestedBrandsDataSource
import com.wemeal.domain.repository.onboarding.GetSuggestedBrandsRepository
import retrofit2.Response

class GetSuggestedBrandsRepositoryImpl(private val getSuggestedBrandsDataSource: GetSuggestedBrandsDataSource) :
    GetSuggestedBrandsRepository {
    override suspend fun getBrands(
    ): Resource<BrandsModel> {
        return responseToResource(
            getSuggestedBrandsDataSource.getBrands()
        )
    }

    private fun responseToResource(response: Response<BrandsModel>): Resource<BrandsModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}