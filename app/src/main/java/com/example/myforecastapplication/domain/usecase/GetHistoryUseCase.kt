package com.example.myforecastapplication.domain.usecase

import com.example.myforecastapplication.base.BaseResult
import com.example.myforecastapplication.data.local.HistoryItem
import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.data.remote.model.ForecastResponse
import com.example.myforecastapplication.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun execute(): Flow<List<WeatherEntity>> {
        return weatherRepository.getAllWeatherHistory()
    }
}