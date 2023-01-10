package com.example.phoneexchangekotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.phoneexchangekotlin.databinding.ActivityMainBinding
import com.example.phoneexchangekotlin.model.CurrencyRate
import com.example.phoneexchangekotlin.model.CurrencyRateKey
import com.example.phoneexchangekotlin.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var viewModelCurrency: CurrencyViewModel
    private lateinit var arrayListKey: ArrayList<String>
    private lateinit var arrayListValues: ArrayList<Double>
    private var valueFromRateDouble: Double = 0.0
    private var valueToRateDouble = 0.0
    private var result: Double = 0.0
    private var valueEdtToCurrently: String = "2.1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        viewModelCurrency = ViewModelProvider(this)[CurrencyViewModel::class.java]
        viewModelCurrency.getCurrencyRate()
        getCRateCurrencyObserver()
        eventFromSpinner()
        eventToSpinner()
        eventFromEditText()
        viewModelCurrency.getLocalRateKey()
        viewModelCurrency.getLocalRateValue()
        observerLocalRateKey()
        observerLocalRateValue()

    }

    @SuppressLint("LogNotTimber")
    fun getCRateCurrencyObserver() {
        viewModelCurrency.getRateCurrency().observe(this) { mapCurrencyRate ->
            Log.i("am", "$mapCurrencyRate")
            val setValues = mapCurrencyRate.values
            val setKey = mapCurrencyRate.keys
            arrayListKey = ArrayList(setKey)
            arrayListValues = ArrayList(setValues)
            updateRateName(arrayListKey)
            updateRateValue(arrayListValues)
            insertRateKey(arrayListKey)
            insertCurrencyRate(arrayListValues)

        }
    }

    private fun insertRateKey(arrayListKey: ArrayList<String>) {
        val rateKey = CurrencyRateKey(
            arrayListKey[0]
            ,arrayListKey[1],
            arrayListKey[2],
            arrayListKey[3],
            arrayListKey[4],
            arrayListKey[5],
            arrayListKey[6],
            arrayListKey[7],
            arrayListKey[8],
            arrayListKey[9],
        )
        this.lifecycleScope.launch {
            viewModelCurrency.getInsertCurrencyKey(rateKey)
        }
    }
    private fun insertCurrencyRate(arrayList: ArrayList<Double>){
        val currencyRate = CurrencyRate(
            arrayList[0],
            arrayList[1],
            arrayList[2],
            arrayList[3],
            arrayList[4],
            arrayList[5],
            arrayList[6],
            arrayList[7],
            arrayList[8],
            arrayList[9],
        )
        this.lifecycleScope.launch{
            viewModelCurrency.getInsertCurrencyValue(currencyRate)
        }
    }

    private fun updateRateName(arrayList: ArrayList<String>) {
        this.arrayListKey = arrayList
        this.initializeFromSpinner()
        this.initializeToSpinner()
    }

    private fun updateRateValue(arrayList: ArrayList<Double>) {
        this.arrayListValues = arrayList
    }

    private fun initializeFromSpinner() {
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListKey)
        arrayAdapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item
        )
        mainBinding.includeMain.currencyFromSpinner.adapter = arrayAdapter
    }

    private fun initializeToSpinner() {
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListKey)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mainBinding.includeMain.currencyToSpinner.adapter = arrayAdapter
    }

    private fun eventFromSpinner() {
        mainBinding.includeMain.currencyFromSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                i: Int,
                l: Long
            ) {
                mainBinding.includeMain.currencyFromEdt.setText("1")
                val valueFromRate: String = mainBinding.includeMain.currencyToEdt.text.toString()
                valueFromRateDouble = valueFromRate.toDouble()
                valueToRateDouble = valueEdtToCurrently.toDouble()
                val result = calacutorRate(valueFromRateDouble, valueToRateDouble, i)
                mainBinding.includeMain.currencyToEdt.setText("$result")

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    @SuppressLint("LogNotTimber")
    fun calacutorRate(
        valueFromRateDouble: Double,
        valueToRateDouble: Double,
        position: Int
    ): Double {
        val rateFromName = arrayListKey[position]

        return if (rateFromName == "USD") {
            val res = valueFromRateDouble * valueToRateDouble
            res
        } else {
            valueFromRateDouble / valueToRateDouble
        }
    }

    private fun eventToSpinner() {
        mainBinding.includeMain.currencyToSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    valueEdtToCurrently = arrayListValues[i].toString()
                    mainBinding.includeMain.currencyToEdt.setText(valueEdtToCurrently)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
    }

    private fun eventFromEditText() {
        mainBinding.includeMain.currencyFromEdt.addTextChangedListener(object : TextWatcher {
            @SuppressLint("LogNotTimber")
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i2: Int, i3: Int) {

            }

            @SuppressLint("LogNotTimber")
            override fun onTextChanged(charSequence: CharSequence?, i1: Int, i2: Int, i3: Int) {
                val valueFromRate = mainBinding.includeMain.currencyFromEdt.text.toString()
                var valueFromRateDouble: Double
                var valueToRateDouble: Double
                try {

                    valueToRateDouble = valueEdtToCurrently.toDouble()
                    valueFromRateDouble = valueFromRate.toDouble()
                } catch (e: NumberFormatException) {
                    valueToRateDouble = 0.0
                    valueFromRateDouble = 0.0
                }

                result = calacutorRate(valueFromRateDouble, valueToRateDouble, i1)
                Log.i("ali1", "$valueFromRate $valueToRateDouble")
                mainBinding.includeMain.currencyToEdt.setText("$result")

            }

            override fun afterTextChanged(editatable: Editable?) {

            }

        })
    }
    @SuppressLint("LogNotTimber")
   private fun observerLocalRateKey() {
        viewModelCurrency.getLocalRateKeyLiveData().observe(this) { listRate ->
            val arrayList:ArrayList<String> = ArrayList()
            for (item in listRate){
                arrayList.add(item.usd)
                arrayList.add(item.aed)
                arrayList.add(item.afn)
                arrayList.add(item.all)
                arrayList.add(item.ars)
                arrayList.add(item.amd)
                arrayList.add(item.ang)
                arrayList.add(item.aoa)
                arrayList.add(item.awg)
                arrayList.add(item.aud)
            }
            updateRateName(arrayList)
            //updateRateValue(arrayListValues)
        }

    }
    @SuppressLint("LogNotTimber")
    private fun observerLocalRateValue(){
        viewModelCurrency.getLocalRateLiveData().observe(this){listRate->
            val arrayList:ArrayList<Double> = ArrayList()
            for (item in listRate){
                arrayList.add(item.usd)
                arrayList.add(item.aed)
                arrayList.add(item.afn)
                arrayList.add(item.all)
                arrayList.add(item.ars)
                arrayList.add(item.amd)
                arrayList.add(item.ang)
                arrayList.add(item.aoa)
                arrayList.add(item.awg)
                arrayList.add(item.aud)
            }

            Log.i("arrayList1","$arrayList")
            updateRateValue(arrayList)

        }
    }


}