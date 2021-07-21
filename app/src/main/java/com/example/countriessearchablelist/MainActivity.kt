package com.example.countriessearchablelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countriessearchablelist.databinding.ActivityMainBinding
import com.example.countriessearchablelist.viewmodel.CountriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val countriesViewModel by viewModel<CountriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countriesViewModel.setCountriesApiResponse()
        countriesViewModel.countriesListApiResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val getResponse = response
            } else {
                Timber.i("Response error code ${response.code()}, error message ${response.message()}")
            }

        })
    }
}