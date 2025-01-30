package com.wemeal.data.model.main.feed.tagged.order

import com.google.gson.annotations.SerializedName
import com.wemeal.data.model.user.DeliveryAddresse
import com.wemeal.data.model.user.UserModel

data class TaggedOrderModel(
    @SerializedName("adminNote")
    val adminNote: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deepLink")
    val deepLink: String?,
    @SerializedName("deliveryAddress")
    val deliveryAddress: DeliveryAddresse?,
    @SerializedName("deliveryFees")
    val deliveryFees: Int?,
    @SerializedName("history")
    val history: List<History>?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isVATIncluded")
    val isVATIncluded: Boolean?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("offers")
    val offers: List<Offer>?,
    @SerializedName("payment")
    val payment: Payment?,
    @SerializedName("place")
    val place: Place?,
    @SerializedName("pmNote")
    val pmNote: String?,
    @SerializedName("products")
    val products: List<Any>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("totalSavings")
    val totalSavings: Int?,
    @SerializedName("transactionID")
    val transactionID: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user")
    val user: UserModel?,
    @SerializedName("__v")
    val v: Int?,
    @SerializedName("VAT")
    val vAT: Any?
)