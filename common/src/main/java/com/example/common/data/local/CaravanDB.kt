package com.example.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.common.domain.model.*

@Database(
    entities = [
        ProductEntity::class,
        UserEntity::class,
        CatsEntity::class,
        ProductItemEntity::class,
        SavedCartOrder::class,
        CardEntity::class
    ],
    version = 4
)
abstract class CaravanDB : RoomDatabase() {

    abstract val caravanDao: CaravanDao

    companion object {
        const val DATABASE_NAME = "caravan_db"
    }

}