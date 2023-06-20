package com.example.caravan.domain.model


import com.google.gson.annotations.SerializedName

data class UserTypeItem(
    @SerializedName("autheId")
    val autheId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)