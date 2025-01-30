package com.wemeal.data.model

import com.google.gson.annotations.SerializedName

data class SuccessModel(
    @SerializedName("result")
    val result: Success?
) : BaseModel() {

    data class Success(
        @SerializedName("success")
        val success: com.wemeal.data.model.Success?
    )
}