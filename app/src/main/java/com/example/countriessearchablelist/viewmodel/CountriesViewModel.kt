package com.example.countriessearchablelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriessearchablelist.model.Country
import com.example.countriessearchablelist.model.CountriesAttributes
import com.example.countriessearchablelist.repository.CountriesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CountriesViewModel(private val countriesRepository: CountriesRepository): ViewModel() {

    private val _countriesListApiResponse: MutableLiveData<Response<List<Country>>> = MutableLiveData()
    private val _countryApiResponse: MutableLiveData<Response<Country>> = MutableLiveData()
    private val _countriesAttributes: MutableLiveData<CountriesAttributes> = MutableLiveData()

    val countriesListApiResponse: LiveData<Response<List<Country>>> get() = _countriesListApiResponse
    val countryApiResponse: LiveData<Response<Country>> get() = _countryApiResponse
    val countriesAttributes: LiveData<CountriesAttributes> get() = _countriesAttributes

    fun setCountriesApiResponse() {
        viewModelScope.launch {
            val response: Response<List<Country>> = countriesRepository.getCountries()
            _countriesListApiResponse.value = response
        }
    }

    fun setCountryAttributesModel(countriesAttributes: CountriesAttributes) {
        viewModelScope.launch {
            _countriesAttributes.value = countriesAttributes
        }
    }

    fun setCountryApiResponse(code: String) {
        viewModelScope.launch {
            val response: Response<Country> = countriesRepository.getCountry(code)
            _countryApiResponse.value = response
        }
    }

}