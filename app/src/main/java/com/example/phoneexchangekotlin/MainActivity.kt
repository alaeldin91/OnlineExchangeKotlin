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
import com.example.phoneexchangekotlin.databinding.ActivityMainBinding
import com.example.phoneexchangekotlin.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                if (valueFromRate == null) {
                    valueFromRateDouble = 1.0
                    valueToRateDouble = valueEdtToCurrently.toDouble()

                }
                else {
                    try {

                        valueToRateDouble = valueEdtToCurrently.toDouble()
                        valueFromRateDouble = valueFromRate.toDouble()
                    } catch (e: NumberFormatException) {
                        valueToRateDouble = 0.0
                        valueFromRateDouble = 0.0
                    }
                }

                result = calacutorRate(valueFromRateDouble, valueToRateDouble, i1)
                Log.i("ali1", "$valueFromRate $valueToRateDouble")
                mainBinding.includeMain.currencyToEdt.setText("$result")

            }

            override fun afterTextChanged(editatable: Editable?) {

            }

        })
    }
}