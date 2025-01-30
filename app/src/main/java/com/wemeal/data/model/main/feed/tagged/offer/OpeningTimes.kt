package com.wemeal.data.model.main.feed.tagged.offer


import com.google.gson.annotations.SerializedName

data class OpeningTimes(
    @SerializedName("Friday")
    val friday: List<Day>?,
    @SerializedName("Monday")
    val monday: List<Day>?,
    @SerializedName("Saturday")
    val saturday: List<Day>?,
    @SerializedName("Sunday")
    val sunday: List<Day>?,
    @SerializedName("Thursday")
    val thursday: List<Day>?,
    @SerializedName("Tuesday")
    val tuesday: List<Day>?,
    @SerializedName("Wednesday")
    val wednesday: List<Day>?
)