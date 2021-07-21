package com.example.countriessearchablelist.api

import com.example.countriessearchablelist.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesApiImpl {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CountriesAPI = retrofit.create(CountriesAPI::class.java)
}