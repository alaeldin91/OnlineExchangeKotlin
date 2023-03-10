package com.example.phoneexchangekotlin.network

import com.example.phoneexchangekotlin.model.CurrencyResult
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("latest/usd")
    fun getCurrency(): Call<CurrencyResult>
}