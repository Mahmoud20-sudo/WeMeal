package com.wemeal.data.model.user


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FacebookUserFriendsData(
    @PrimaryKey
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)