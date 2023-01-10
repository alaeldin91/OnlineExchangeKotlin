package com.example.phoneexchangekotlin.repository

import androidx.lifecycle.LiveData
import com.example.phoneexchangekotlin.db.CurrencyDao
import com.example.phoneexchangekotlin.model.CurrencyRate
import com.example.phoneexchangekotlin.model.CurrencyRateKey
import com.example.phoneexchangekotlin.model.CurrencyResult
import com.example.phoneexchangekotlin.network.ApiService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Call
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class CurrencyRepository @Inject constructor(apiService: ApiService, currencyDao: CurrencyDao) {
    private var apiService: ApiService
    private var currencyDao: CurrencyDao

    init {
        this.apiService = apiService
        this.currencyDao = currencyDao
    }

    fun getCurrency(): Call<CurrencyResult> {
        return apiService.getCurrency()
    }

    suspend fun insertCurrencyRateValue(currencyRate: CurrencyRate) {
        return currencyDao.insertCurrencyRate(currencyRate)
    }

    suspend fun insertCurrencyRateKey(currencyRateKey: CurrencyRateKey) {
        return currencyDao.insertCurrencyRateKey(currencyRateKey)
    }

    fun getRateValue(): LiveData<List<CurrencyRate>> {
        return currencyDao.getCurrencyRateValue()
    }

    fun getRateKey(): LiveData<List<CurrencyRateKey>> {
        return currencyDao.getCurrentRateKey()
    }

}