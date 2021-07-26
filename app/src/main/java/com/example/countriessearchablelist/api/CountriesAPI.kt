package com.example.countriessearchablelist.api

import com.example.countriessearchablelist.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesAPI {

    @GET("/rest/v2/all")
    suspend fun getCountries(): Response<List<Country>>

    @GET("/rest/v2/alpha/{code}")
    suspend fun getCountry(@Path("code") code: String): Response<Country>
}