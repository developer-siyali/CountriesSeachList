package com.example.countriessearchablelist.model

data class Country (
    val name: String,
    val topLevelDomain: List<String>,
    val alpha2Code: String,
    val alpha3Code: String,
    val callingCodes: List<String>,
    val capital: String,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String,
    val population: Int,
    val latlng: List<Double>,
    val demonym: String,
    val area: Double,
    val gini: Double,
    val timeszones: List<String>,
    val borders: List<String>,
    val nativeName: String,
    val numericCode: String,
    val currencies: List<CountryCurrency>,
    val languages: List<CountryLanguage>,
    val translations: LanguageTranslation,
    val flag: String,
    val regionalBlocs: List<RegionalBloc>,
    val cioc: String
)
