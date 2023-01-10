package com.example.phoneexchangekotlin.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phoneexchangekotlin.model.CurrencyRate
import com.example.phoneexchangekotlin.model.CurrencyRateKey
import com.example.phoneexchangekotlin.model.CurrencyResult
import com.example.phoneexchangekotlin.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    repository: CurrencyRepository
) : ViewModel() {
    private var repository: CurrencyRepository
    private var currencyRateMap: MutableLiveData<HashMap<String, Double>> = MutableLiveData()
    private var currencyRateValueLocal: LiveData<List<CurrencyRate>>
    private var currencyRateKeyLocal: LiveData<List<CurrencyRateKey>>


    init {
        this.repository = repository
        this.currencyRateValueLocal = repository.getRateValue()
        this.currencyRateKeyLocal = repository.getRateKey()
    }

    fun getCurrencyRate() {
        repository.getCurrency().enqueue(object : Callback<CurrencyResult> {
            @SuppressLint("LogNotTimber")
            override fun onResponse(
                call: Call<CurrencyResult>,
                response: Response<CurrencyResult>
            ) {
                response.let { currencyResult ->
                    val currencyRate = currencyResult.body()!!.rate
                    val hashMapRate: HashMap<String, Double> = HashMap()
                    hashMapRate["USD"] = currencyRate.usd
                    hashMapRate["AED"] = currencyRate.aed
                    hashMapRate["AFN"] = currencyRate.afn
                    hashMapRate["ALL"] = currencyRate.all
                    hashMapRate["AMD"] = currencyRate.amd
                    hashMapRate["ANG"] = currencyRate.ang
                    hashMapRate["AOA"] = currencyRate.aoa
                    hashMapRate["ARS"] = currencyRate.ars
                    hashMapRate["AUD"] = currencyRate.aud
                    hashMapRate.put("AWG", currencyRate.awg)
                    currencyRateMap.postValue(hashMapRate)

                }
            }

            @SuppressLint("LogNotTimber")
            override fun onFailure(call: Call<CurrencyResult>, t: Throwable) {
                Log.i("error", t.message.toString())
            }

        })
    }

    fun getRateCurrency(): MutableLiveData<HashMap<String, Double>> {
        return currencyRateMap
    }

    suspend fun getInsertCurrencyKey(currencyRateKey: CurrencyRateKey) {
        this.repository.insertCurrencyRateKey(currencyRateKey)
    }

    fun getLocalRateLiveData(): LiveData<List<CurrencyRate>> {
        return repository.getRateValue()
    }

    fun getLocalRateValue() {
        this.currencyRateValueLocal = repository.getRateValue()
    }

    fun getLocalRateKeyLiveData(): LiveData<List<CurrencyRateKey>> {
        return repository.getRateKey()
    }

    fun getLocalRateKey() {
        this.currencyRateKeyLocal = repository.getRateKey()
    }

    suspend fun getInsertCurrencyValue(currencyRate: CurrencyRate) {
        this.repository.insertCurrencyRateValue(currencyRate)

    }
}