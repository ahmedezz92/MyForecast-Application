package com.example.myforecastapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val country: String,
    val region: String,
    val temperatureC: Double,
    val temperatureF: Double,
    val conditionIcon: String,
    val conditionText: String,
    val windDegree: Int,
    val windSpeed: Double,
    val humidity: Int,
    val lastUpdate: String,
    val feelslike: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val imagePath: String
)