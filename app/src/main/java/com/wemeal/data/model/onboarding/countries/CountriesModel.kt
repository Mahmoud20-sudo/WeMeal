package com.wemeal.data.model.onboarding.countries


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.BaseModel

data class CountriesModel(
    @SerializedName("result")
    var result: List<Result>?
) : BaseModel()