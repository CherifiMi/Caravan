package com.example.caravan.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.caravan.data.repository.CaravanRepository
import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("picUrl")
    val picUrl: String,
    @SerializedName("subCats")
    val subCats: List<String>
)

class CatList : ArrayList<Cat>()

@Entity
data class CatsEntity(
    @PrimaryKey val id: Int? = null,
    val cat: String
)