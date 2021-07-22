package com.example.countriessearchablelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import com.example.countriessearchablelist.databinding.ActivityMainBinding
import com.example.countriessearchablelist.model.CountryNamesList
import com.example.countriessearchablelist.viewmodel.CountriesViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val countriesViewModel by viewModel<CountriesViewModel>()
    private var countryNamesList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countriesViewModel.setCountriesApiResponse()
        countriesViewModel.countriesListApiResponse.observe(this, { response ->
            if (response.isSuccessful) {
                response.body()?.filter { country ->
                    countryNamesList.add(country.name)
                }
                countriesViewModel.setCountryNamesList(CountryNamesList(countryNamesList))
                val listAdapter = ArrayAdapter(this, R.layout.adapter_county_name, countryNamesList.toTypedArray())
                binding.autoCompleteView.setAdapter(listAdapter)
                binding.autoCompleteView.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        enableSearchButton(s.toString(), countryNamesList)
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }

                })
            } else {
                Timber.i("Response error code ${response.code()}, error message ${response.message()}")
            }
        })
    }

    private fun enableSearchButton(userInput: String, countryNamesList: List<String>) {
        binding.searchButton.isClickable = userInput in countryNamesList
    }
}