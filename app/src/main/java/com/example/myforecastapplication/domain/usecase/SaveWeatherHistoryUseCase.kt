package com.example.myforecastapplication.domain.usecase

import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.data.remote.model.WeatherResponse
import com.example.myforecastapplication.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveWeatherHistoryUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(weatherData: WeatherResponse, imagePath: String) {
        val weatherHistoryItem = WeatherEntity(
            timestamp = System.currentTimeMillis(),
            temperatureF = weatherData.current.temp_f,
            temperatureC = weatherData.current.temp_c,
            humidity = weatherData.current.humidity,
            windSpeed = weatherData.current.wind_kph,
            region = weatherData.location.region,
            country = weatherData.location.country,
            cityName = weatherData.location.name,
            conditionIcon = weatherData.current.condition.icon,
            conditionText = weatherData.current.condition.text,
            feelslike = weatherData.current.feelslike_c,
            lastUpdate = weatherData.current.last_updated,
            windDegree = weatherData.current.wind_degree,
            imagePath = imagePath
        )
        weatherRepository.saveWeatherHistory(weatherHistoryItem)
    }
}