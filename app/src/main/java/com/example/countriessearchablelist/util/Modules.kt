package com.example.countriessearchablelist.util

import com.example.countriessearchablelist.api.CountriesApiImpl
import com.example.countriessearchablelist.repository.CountriesRepository
import com.example.countriessearchablelist.view.CountriesExpandableListAdapter
import com.example.countriessearchablelist.viewmodel.CountriesViewModel
import org.koin.dsl.module

val appModule = module {

    single { CountriesApiImpl().api }

    single { CountriesRepository(get()) }

    single { CountriesViewModel(get()) }

    single { CountriesExpandableListAdapter() }

}