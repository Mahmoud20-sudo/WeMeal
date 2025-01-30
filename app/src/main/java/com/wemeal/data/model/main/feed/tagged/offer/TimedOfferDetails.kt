package com.wemeal.data.model.main.feed.tagged.offer

import com.google.gson.annotations.SerializedName

data class TimedOfferDetails(
    @SerializedName("days")
    val days: List<String>?,
    @SerializedName("endTime")
    val endTime: TimeModel?,
    @SerializedName("startTime")
    val startTime: TimeModel?
)