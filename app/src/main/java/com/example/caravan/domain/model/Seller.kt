package com.example.caravan.domain.model


import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("autheId")
    val autheId: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("ordersId")
    val ordersId: List<String>?,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("productsId")
    val productsId: List<String>?,
    @SerializedName("type")
    val type: String,
    @SerializedName("stripeId")
    val stripeId: String,
)

class SellerList : ArrayList<Seller>()