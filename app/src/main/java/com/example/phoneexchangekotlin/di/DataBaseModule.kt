package com.example.phoneexchangekotlin.di

import android.app.Application
import androidx.room.Room
import com.example.phoneexchangekotlin.db.CurrencyDb
import com.example.phoneexchangekotlin.network.ApiService
import com.example.phoneexchangekotlin.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideCurrentDb(application: Application): CurrencyDb {
        return Room.databaseBuilder(application, CurrencyDb::class.java, "currencyDb")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }
    @Singleton
    @Provides
    fun provideCurrencyRepository(apiService: ApiService,currencyDb: CurrencyDb):CurrencyRepository{
        return CurrencyRepository(apiService,currencyDb.currencyDao())
    }
}