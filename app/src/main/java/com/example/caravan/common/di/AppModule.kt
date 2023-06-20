package com.example.caravan.common.di

import android.app.Application
import androidx.room.Room
import com.example.caravan.data.local.CaravanDB
import com.example.caravan.data.local.CaravanDao
import com.example.caravan.data.remote.CaravanApi
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePizzaApi(): CaravanApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CaravanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCaravanDatabase(app: Application): CaravanDB{
        return Room.databaseBuilder(
            app,
            CaravanDB::class.java,
            CaravanDB.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(api: CaravanApi, db: CaravanDB): CaravanRepository {
        return CaravanRepository(api, db.caravanDao)
    }

    @Provides
    @Singleton
    fun provideAccountService(): AccountService {
        return AccountService()
    }
}