package com.example.countriessearchablelist.repository

import com.example.countriessearchablelist.model.Country
import com.example.countriessearchablelist.api.CountriesAPI
import retrofit2.Response

class CountriesRepository(private val countriesApi: CountriesAPI) {

    suspend fun getCountries(): Response<List<Country>> {
        return countriesApi.getCountries()
    }
}