package com.example.myforecastapplication.domain.usecase

import com.example.myforecastapplication.base.BaseResult
import com.example.myforecastapplication.data.remote.model.WeatherResponse
import com.example.myforecastapplication.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityCurrentWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun execute(latitude:Double,longitude:Double ): Flow<BaseResult<WeatherResponse>> {
        return weatherRepository.getCurrentWeather(latitude = latitude, longitude = longitude)
    }
}