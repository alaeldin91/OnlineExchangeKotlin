package com.example.phoneexchangekotlin.model

import com.google.gson.annotations.SerializedName

data class CurrencyResult(
    @SerializedName("result")
    var result: String,
    @SerializedName("rates")
    var rate: CurrencyRate
)
