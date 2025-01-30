package com.wemeal.data.model.main.tagged.orders

import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.NameModel
import com.wemeal.data.model.main.feed.tagged.order.Offer
import com.wemeal.data.model.main.tagged.products.PlaceBrand
import okhttp3.internal.format

data class Result(
    val _id: String,
    val title: NameModel,
    val place: PlaceBrand,
    val createdAt: String,
    val imageURL: String,
    val products: List<Product>,
    val offers: List<Offer>,
    val status: String,
    val totalPrice: Int,
    val totalSavings: Int,
    val transactionID: String,
    val updatedAt: String,
    val orderable: Boolean?,
    val nonOrderableReason : String?
)