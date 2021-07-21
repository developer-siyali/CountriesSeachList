package com.example.countriessearchablelist.util

import com.example.countriessearchablelist.api.CountriesApiImpl
import com.example.countriessearchablelist.repository.CountriesRepository
import com.example.countriessearchablelist.viewmodel.CountriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { CountriesApiImpl().api }

    factory { CountriesRepository(get()) }

    viewModel { CountriesViewModel(get()) }
}