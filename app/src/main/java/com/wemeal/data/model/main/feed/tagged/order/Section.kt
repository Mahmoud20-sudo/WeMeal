package com.wemeal.data.model.main.feed.tagged.order


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel

data class Section(
    @SerializedName("description")
    val description: NameModel?,
    @SerializedName("discountSection")
    val discountSection: Boolean?,
    @SerializedName("freeSection")
    val freeSection: Boolean?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("maximumChoice")
    val maximumChoice: Int?,
    @SerializedName("minimumChoice")
    val minimumChoice: Int?,
    @SerializedName("multipleChoice")
    val multipleChoice: Boolean?,
    @SerializedName("required")
    val required: Boolean?,
    @SerializedName("title")
    val title: NameModel?
)
