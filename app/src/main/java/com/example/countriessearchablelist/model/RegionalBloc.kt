package com.example.countriessearchablelist.model

data class RegionalBloc (
    val acronym: String,
    val name: String,
    val otherAcronyms: List<String>,
    val otherName: List<String>
)
