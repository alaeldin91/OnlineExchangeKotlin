package com.example.phoneexchangekotlin.di

import com.example.phoneexchangekotlin.network.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val baseUrl = "https://open.er-api.com/v6/"

    @Provides
    @Singleton
    fun provideMealApiService(): ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
            GsonConverterFactory
                .create(gson)
        ).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
            .create(ApiService::class.java)
    }
}