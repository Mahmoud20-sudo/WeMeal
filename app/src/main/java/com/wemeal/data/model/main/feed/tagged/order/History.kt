package com.wemeal.data.model.main.feed.tagged.order


import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("by")
    val `by`: By?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updateReason")
    val updateReason: String?
)