package com.wemeal.data.model.main.feed.tagged.order

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("isPaid")
    val isPaid: Boolean?,
    @SerializedName("method")
    val method: String?
)