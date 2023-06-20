package com.example.caravan.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("cat")
    val cat: List<String>,
    @SerializedName("content")
    val content: String,
    @SerializedName("id")
    val id: String?,
    @SerializedName("imageUrls")
    val imageUrls: List<String>,
    @SerializedName("initPrice")
    val initPrice: Int,
    @SerializedName("minOrder")
    val minOrder: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("newPrice")
    val newPrice: Int,
    @SerializedName("sellerKey")
    val sellerKey: String,
    @SerializedName("sellerStripe")
    val sellerStripe: String,
    @SerializedName("amountInInv")
    val amountInInv: Int,
)

class ProductsList : ArrayList<Product>()

@Entity
data class ProductEntity(
    @PrimaryKey val id: Int? = null,
    val productList: String
)

@Entity
data class ProductItemEntity(
    @PrimaryKey val id: Int? = null,
    val product: String,
    val amount: Int
)