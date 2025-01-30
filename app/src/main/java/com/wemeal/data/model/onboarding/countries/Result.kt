package com.wemeal.data.model.onboarding.countries


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel
import com.wemeal.data.model.user.Location

data class Result(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("dialCode")
    val dialCode: String?,
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("name")
    val name: NameModel?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("__v")
    val v: Int?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("areaOnlyName")
    val areaOnlyName: NameModel?,
    @SerializedName("subAreaOnlyName")
    val subAreaOnlyName: NameModel?,
    @SerializedName("hasSubareas")
    val hasSubareas: Boolean?,
    @SerializedName("location")
    val location: Location?
) {
    override fun equals(other: Any?): Boolean {
        if (other is Result) {
            if (_id == other._id) return true
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}