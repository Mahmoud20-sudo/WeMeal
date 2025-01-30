package com.wemeal.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wemeal.data.model.user.DeliveryAddresse
import com.wemeal.data.model.user.FacebookUserFriendsData

class Converters {
    @TypeConverter
    fun fromDeliveryAddresse(value: List<DeliveryAddresse>): String {
        val gson = Gson()
        val type = object : TypeToken<List<DeliveryAddresse>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDeliveryAddresse(value: String): List<DeliveryAddresse> {
        val gson = Gson()
        val type = object : TypeToken<List<DeliveryAddresse>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromFacebookUserFriendsData(value: List<FacebookUserFriendsData>): String {
        val gson = Gson()
        val type = object : TypeToken<List<FacebookUserFriendsData>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toFacebookUserFriendsData(value: String): List<FacebookUserFriendsData> {
        val gson = Gson()
        val type = object : TypeToken<List<FacebookUserFriendsData>>() {}.type
        return gson.fromJson(value, type)
    }
}