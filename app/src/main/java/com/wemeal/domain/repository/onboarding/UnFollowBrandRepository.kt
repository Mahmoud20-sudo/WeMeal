package com.wemeal.domain.repository.onboarding

import androidx.paging.PagingSource
import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.model.onboarding.countries.Result

interface UnFollowBrandRepository {
    suspend fun unFollowBrand(brandId : String): Resource<SuccessModel>
}