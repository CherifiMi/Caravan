package com.example.caravan.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardEntity(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val exM: Int,
    val exY: Int,
    val cvc: Int,
    val num: Int
)