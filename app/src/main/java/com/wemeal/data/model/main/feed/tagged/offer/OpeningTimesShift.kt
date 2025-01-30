package com.wemeal.data.model.main.feed.tagged.offer


import com.google.gson.annotations.SerializedName

data class OpeningTimesShift(
    @SerializedName("endTime")
    val endTime: TimeModel?,
    @SerializedName("startTime")
    val startTime: TimeModel?
)