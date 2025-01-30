package com.wemeal.data.model.main.feed.tagged.offer


import com.google.gson.annotations.SerializedName

data class TimeModel(
    @SerializedName("hour")
    val hour: Int?,
    @SerializedName("minute")
    val minute: Int?
)