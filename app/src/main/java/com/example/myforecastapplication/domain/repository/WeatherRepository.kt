package com.example.myforecastapplication.domain.repository

import com.example.myforecastapplication.base.BaseResult
import com.example.myforecastapplication.data.local.HistoryItem
import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.data.remote.model.ForecastResponse
import com.example.myforecastapplication.data.remote.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude:Double,longitude:Double): Flow<BaseResult<WeatherResponse>>
    suspend fun getHistory():Flow<List<HistoryItem>>

    suspend fun saveWeatherHistory(weatherHistoryItem: WeatherEntity)
    suspend  fun getAllWeatherHistory(): Flow<List<WeatherEntity>>

}