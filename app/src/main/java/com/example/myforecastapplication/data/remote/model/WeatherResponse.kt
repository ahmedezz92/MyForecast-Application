package com.example.myforecastapplication.data.remote.model

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
)

data class Current(
    val last_updated: String,
    val temp_c: Double,
    val temp_f:Double,
    val condition: Condition,
    val wind_kph: Double,
    val wind_degree: Int,
    val humidity: Int,
    val feelslike_c: Double,
)

data class Condition(
    val text: String,
    val icon: String,
)

