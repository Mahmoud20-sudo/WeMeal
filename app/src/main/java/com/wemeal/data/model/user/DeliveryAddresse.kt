package com.wemeal.data.model.user


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.user.Area
import com.wemeal.data.model.user.City
import com.wemeal.data.model.user.Country

@Entity
data class DeliveryAddresse(
    @PrimaryKey
    @SerializedName("_id")
    val id: String?,
    @SerializedName("area")
    val area: Area?,
    @SerializedName("branchedStreet")
    val branchedStreet: String?,
    @SerializedName("building")
    val building: String?,
    @SerializedName("city")
    val city: City?,
    @SerializedName("country")
    val country: Country?,
    @SerializedName("flat")
    val flat: String?,
    @SerializedName("floor")
    val floor: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("nearestLandmark")
    val nearestLandmark: String?,
    @SerializedName("street")
    val street: String?
)