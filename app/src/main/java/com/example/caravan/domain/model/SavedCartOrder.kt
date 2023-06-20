package com.example.caravan.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedCartOrder(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val price: Int,
    val amount: Int,
    val sellerId: String,
    val buyerId: String,
    val firstPicUrl: String,
    val productId: String,
    val sellerStripe: String
)

