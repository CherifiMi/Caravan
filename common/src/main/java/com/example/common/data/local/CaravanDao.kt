package com.example.common.data.local

import androidx.room.*
import com.example.caravan.domain.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CaravanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllProducts(note: ProductEntity)

    @Query("SELECT * FROM productentity")
    suspend fun getAllProducts(): ProductEntity

    @Query("DELETE FROM productentity")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(note: UserEntity)

    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): UserEntity

    @Query("DELETE FROM UserEntity")
    suspend fun deleteUser()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCats(cats: CatsEntity)

    @Query("SELECT * FROM CatsEntity")
    suspend fun getCats(): CatsEntity

    @Query("DELETE FROM CatsEntity")
    suspend fun deleteCats()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(cats: ProductItemEntity)

    @Query("SELECT * FROM ProductItemEntity")
    suspend fun getItem(): ProductItemEntity

    @Query("DELETE FROM ProductItemEntity")
    suspend fun deleteItem()


    //cart stuff
    /* * delete all*/
    @Query("DELETE FROM SavedCartOrder")
    suspend fun deleteAllCartOrder()

    /* * get all*/
    @Query("SELECT * FROM SavedCartOrder")
    suspend fun getAllCartOrder(): List<SavedCartOrder>

    /* * add one*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCartOrder(order: SavedCartOrder)

    //card
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCard(cardEntity: CardEntity)

    @Query("SELECT * FROM CardEntity")
    suspend fun getCard(): CardEntity

}