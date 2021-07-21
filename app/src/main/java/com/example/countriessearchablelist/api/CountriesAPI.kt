package com.example.countriessearchablelist.api

import com.example.countriessearchablelist.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountriesAPI {

    @GET("/rest/v2/all")
    suspend fun getCountries(): Response<List<Country>>
}