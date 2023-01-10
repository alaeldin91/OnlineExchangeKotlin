package com.example.phoneexchangekotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.phoneexchangekotlin.model.CurrencyRate
import com.example.phoneexchangekotlin.model.CurrencyRateKey

@Database(
    entities = [CurrencyRateKey::class, CurrencyRate::class],
    version = 1,
    exportSchema = false)
abstract class CurrencyDb : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

}