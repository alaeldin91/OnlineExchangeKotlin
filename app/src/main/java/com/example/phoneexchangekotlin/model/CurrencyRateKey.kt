package com.example.phoneexchangekotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencyRateKey")
data class CurrencyRateKey(
    @PrimaryKey(autoGenerate = false)
    var usd: String,
    var aed: String,
    var afn: String,
    var all: String,
    var amd: String,
    var ang: String,
    var aoa: String,
    var ars: String,
    var aud: String,
    var awg: String
) {

}