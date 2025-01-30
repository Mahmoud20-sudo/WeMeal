package com.wemeal.data.repository.datasourceImpl.onboarding

import com.wemeal.data.api.ApiServices
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.params.add_area.AddAreaModel
import com.wemeal.data.model.params.add_area.AreaInfo
import com.wemeal.data.repository.datasource.onboarding.ConfirmAreaDataSource
import retrofit2.Response

class ConfirmAreaDataSourceImpl(private val apiServices: ApiServices) : ConfirmAreaDataSource {
    override suspend fun confirmArea(
        areaModel: com.wemeal.data.model.onboarding.nearest.Result
    ): Response<SuccessModel> {
        val addAreaModel = AddAreaModel(
            areaId = areaModel.id,
            areaInfo = if (areaModel.id.isNullOrEmpty()) areaToInfo(areaModel) else null
        )
        return apiServices.confirmArea(addAreaModel = addAreaModel)
    }

    private fun areaToInfo(areaModel: com.wemeal.data.model.onboarding.nearest.Result): AreaInfo {
        return AreaInfo(
            name = areaModel.name,
            country = areaModel.country?.name,
            city = areaModel.city?.name,
            lat = areaModel.location?.coordinates?.get(1),
            lng = areaModel.location?.coordinates?.get(0),
        )
    }
}
