package com.wemeal.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserSettings(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("language")
    val language: String?
)