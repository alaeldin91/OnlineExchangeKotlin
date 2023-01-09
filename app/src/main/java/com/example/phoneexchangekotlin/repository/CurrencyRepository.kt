package com.example.phoneexchangekotlin.repository

import com.example.phoneexchangekotlin.network.ApiService
import com.example.phoneexchangekotlin.nodel.CurrencyResult
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Call
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class CurrencyRepository @Inject constructor(apiService: ApiService) {
    private var apiService: ApiService

    init {
        this.apiService = apiService
    }

    fun getCurrency(): Call<CurrencyResult> {
        return apiService.getCurrency()
    }
}