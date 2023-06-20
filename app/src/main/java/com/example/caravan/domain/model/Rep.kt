package com.example.caravan.domain.model


import com.google.gson.annotations.SerializedName

data class Rep(
    @SerializedName("autheId")
    val autheId: String,
    @SerializedName("id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("myBuyers")
    val myBuyers: List<String>?,
    @SerializedName("mySellers")
    val mySellers: List<String>?,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
)

class RepList : ArrayList<Rep>()