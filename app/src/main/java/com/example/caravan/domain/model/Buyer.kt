package com.example.caravan.domain.model


import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit

data class Buyer(
    @SerializedName("id")
    val id: String?,
    @SerializedName("address")
    val address: String,
    @SerializedName("autheId")
    val autheId: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("phone")
    val phone: String
)


class BuyersList : ArrayList<Buyer>()

class Payout: ArrayList<String> ()