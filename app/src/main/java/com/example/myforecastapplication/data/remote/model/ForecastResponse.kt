package com.example.myforecastapplication.data.remote.model

data class ForecastResponse(
    val weatherResponse: WeatherResponse,
    val forecast: Forecast
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day,
    val astro: Astro
)

data class Day(
    val maxtemp_c: String,
    val maxtemp_f: String,
    val mintemp_c: String,
    val condition: Condition,
    val avghumidity: Int
)
data class Astro(
    val sunrise:String,
    val sunset:String,
    val moonrise:String,
    val moonset:String
)