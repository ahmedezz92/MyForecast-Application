package com.example.myforecastapplication.data.local

data class HistoryItem(
    val imagePath: String,
    val date: String,
    val temperature: String,
    val humidity: String,
    val windSpeed: String,
    val windDegree:String,
    val locationName:String,
    val feelsLike:String,
    val conditionIcon:String
)