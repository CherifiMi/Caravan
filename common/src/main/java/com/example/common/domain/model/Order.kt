package com.example.common.domain.model


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("buyer")
    val buyer: String,
    @SerializedName("id")
    val id: String?,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("seller")
    val seller: String
)

data class OrderItem(
    val amount: Int,
    val product: Product
)

class OrderList : ArrayList<Order>()