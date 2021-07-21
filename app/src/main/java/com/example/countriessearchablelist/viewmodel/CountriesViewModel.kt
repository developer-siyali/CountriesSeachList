package com.example.countriessearchablelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriessearchablelist.model.Country
import com.example.countriessearchablelist.model.CountryNamesList
import com.example.countriessearchablelist.repository.CountriesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CountriesViewModel(private val countriesRepository: CountriesRepository): ViewModel() {

    private val _countriesListApiResponse: MutableLiveData<Response<List<Country>>> = MutableLiveData()
    private val _countryNamesList: MutableLiveData<CountryNamesList> = MutableLiveData()

    val countriesListApiResponse: LiveData<Response<List<Country>>> get() = _countriesListApiResponse
    val countryNamesList: LiveData<CountryNamesList> get() = _countryNamesList

    fun setCountriesApiResponse() {
        viewModelScope.launch {
            val response: Response<List<Country>> = countriesRepository.getCountries()
            _countriesListApiResponse.value = response
        }
    }

    fun setCountryNamesList(countryNamesList: CountryNamesList) {
        viewModelScope.launch {
            _countryNamesList.value = countryNamesList
        }
    }

}