package com.example.phoneexchangekotlin.nodel

import com.google.gson.annotations.SerializedName

data class CurrencyRate(
    @SerializedName("USD")
    var usd: Double,
    @SerializedName("AED")
    var aed: Double,
    @SerializedName("AFN")
    var afn: Double,
    @SerializedName("ALL")
    var all: Double,
    @SerializedName("AMD")
    var amd: Double,
    @SerializedName("ANG")
    var ang: Double,
    @SerializedName("AOA")
    var aoa: Double,
    @SerializedName("ARS")
    var ars: Double,
    @SerializedName("AUD")
    var aud: Double,
    @SerializedName("AWG")
    var awg: Double
)
