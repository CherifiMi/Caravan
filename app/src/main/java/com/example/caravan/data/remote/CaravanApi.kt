package com.example.caravan.data.remote

import com.example.caravan.domain.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CaravanApi {


    @POST("buyers")
    suspend fun postNewBuyer(@Body buyer: Buyer): ResponseBody

    @POST("sellers")
    suspend fun postNewSeller(@Body buyer: Seller): ResponseBody

    @POST("reps")
    suspend fun postNewRep(@Body buyer: Rep): ResponseBody

    @POST("type")
    suspend fun getUserType(@Body id: Id): UserType


    //_____________________________________________________________________________
    @POST("sellers/auth")
    suspend fun getSellerByKey(@Body id: Id): ResponseBody

    @POST("buyers/auth")
    suspend fun getBuyerByKey(@Body id: Id): ResponseBody

    @POST("reps/auth")
    suspend fun getRepByKey(@Body id: Id): ResponseBody



    //_____________________________________________________________________________
    @POST("product/all")
    suspend fun getAllSellerProducts(@Body id: Id): ProductsList

    @GET("product")
    suspend fun getProducts(): ProductsList

    @POST("product")
    suspend fun createNewProduct(@Body product: Product): ResponseBody

    @PUT("product")
    suspend fun changeThisProduct(@Body product: Product): ResponseBody

    @POST("product/delete")
    suspend fun deleteThisProduct(@Body id: Id): ResponseBody

    @POST("product/id")
    suspend fun getProductById(@Body id: Id): ResponseBody
    //_____________________________________________________________________________
    @GET("cats")
    suspend fun getCats(): ResponseBody



    //_____________________________________________________________________________
    @POST("payment")
    suspend fun paymentIntent(@Body requestBody: RequestBody): ResponseBody

    @POST("accountLink")
    suspend fun accountLink(@Body id: Id): ResponseBody

    //_____________________________________________________________________________
    @POST("orders/make")
    suspend fun makeOrder(@Body order: Order)
    @POST("orders/lowerinv")
    suspend fun makeOrderLowerInv(@Body order: Order)


    @POST("orders/delete")
    suspend fun deleteOrder(@Body id: Id)

    @POST("orders/my")
    suspend fun myOrder(@Body id: Id): ResponseBody

}