package com.example.phoneexchangekotlin.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phoneexchangekotlin.nodel.CurrencyResult
import com.example.phoneexchangekotlin.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(repository: CurrencyRepository) : ViewModel() {
    private var repository: CurrencyRepository
    private var currencyRateMap: MutableLiveData<HashMap<String, Double>> = MutableLiveData()

    init {
        this.repository = repository
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

}