package com.wemeal.data.model.main.feed.tagged.offer


import com.google.gson.annotations.SerializedName

data class CreatedBy(
    @SerializedName("name")
    val name: String?,
    @SerializedName("role")
    val role: String?
)