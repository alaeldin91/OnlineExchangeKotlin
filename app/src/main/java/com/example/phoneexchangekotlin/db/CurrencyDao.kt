package com.example.phoneexchangekotlin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.phoneexchangekotlin.model.CurrencyRate
import com.example.phoneexchangekotlin.model.CurrencyRateKey

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRate(currencyRate: CurrencyRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRateKey(currencyRateKey: CurrencyRateKey)

    @Query("SELECT * FROM currencyRate")
    fun getCurrencyRateValue(): LiveData<List<CurrencyRate>>

    @Query("SELECT * FROM currencyRateKey")
    fun getCurrentRateKey(): LiveData<List<CurrencyRateKey>>
}