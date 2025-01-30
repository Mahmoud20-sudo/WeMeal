package com.wemeal.data.model.main.tagged.offers


import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel
import com.wemeal.data.model.main.tagged.products.PlaceBrand

data class Result(
    @SerializedName("description")
    val description: NameModel?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("galleryUrls")
    val galleryUrls: List<String>?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("pictureUrl")
    val pictureUrl: String?,
    @SerializedName("place")
    val place: Place?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("title")
    val title: NameModel?,
    @SerializedName("orderable")
    val orderable: Boolean?,
    @SerializedName("nonOrderableReason")
    val nonOrderableReason: String?

//    @SerializedName("placeId")
//    val placeId: PlaceBrand?,

)