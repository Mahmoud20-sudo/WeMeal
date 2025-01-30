package com.wemeal.data.model.user


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

@Entity
data class Country(
    @PrimaryKey
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: NameModel?
)