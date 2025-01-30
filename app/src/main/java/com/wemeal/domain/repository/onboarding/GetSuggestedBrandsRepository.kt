package com.wemeal.domain.repository.onboarding

import com.wemeal.data.model.Resource
import com.wemeal.data.model.onboarding.suggested_brands.BrandsModel
import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel

interface GetSuggestedBrandsRepository {
    suspend fun getBrands(): Resource<BrandsModel>
}