package com.wemeal.data.model.main


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("profilePictureUrl")
    val profilePictureUrl: String?,
    @SerializedName("username")
    val username: String?
)