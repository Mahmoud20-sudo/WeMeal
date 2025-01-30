package com.wemeal.data.model.main.create


import com.google.gson.annotations.SerializedName

data class CreatePostRequestModel(
    @SerializedName("body")
    val body: String?,
    @SerializedName("taggedObject")
    val taggedObject: String?,
    @SerializedName("objectType")
    val objectType: String?,
    @SerializedName("shareToBrand")
    val shareToBrand: Boolean?,
    @SerializedName("images")
    val images: List<AwsRequestModel>?
)