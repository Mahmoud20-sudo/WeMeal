package com.wemeal.data.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class NameModel(
    @SerializedName("abbr")
    val abbr: String?,
    @SerializedName("ar")
    val ar: String?,
    @SerializedName("en")
    val en: String?,
    @SerializedName("errorType")
    val errorType: String?,
    @SerializedName("message")
    val message: String?

)