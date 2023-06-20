package com.example.caravan.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class UserType : ArrayList<UserTypeItem>()

@Entity
data class UserEntity(
    @PrimaryKey val id: Int? = null,
    val user: String
)