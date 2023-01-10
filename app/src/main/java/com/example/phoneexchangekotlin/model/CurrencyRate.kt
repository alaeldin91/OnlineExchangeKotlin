package com.example.phoneexchangekotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "currencyRate")
 class CurrencyRate(
    @PrimaryKey(autoGenerate = false)
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
