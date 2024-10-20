package com.example.myforecastapplication.data.repository

import com.example.myforecastapplication.data.remote.model.WeatherResponse
import com.example.myforecastapplication.base.BaseResult
import com.example.myforecastapplication.core.data.utils.ErrorResponse
import com.example.myforecastapplication.core.data.utils.WrappedErrorResponse
import com.example.myforecastapplication.data.local.HistoryItem
import com.example.myforecastapplication.data.local.WeatherDao
import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.data.remote.api.WeatherApiService
import com.example.myforecastapplication.data.remote.model.ForecastResponse
import com.example.myforecastapplication.domain.repository.WeatherRepository
import com.example.myforecastapplication.utils.Constants
import com.example.myforecastapplication.utils.toCurrentWeather
import com.example.myforecastapplication.utils.toWeatherEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val movieApiService: WeatherApiService,
    private val weatherDao: WeatherDao,
) :
    WeatherRepository {
    companion object {
        private const val CACHE_TIMEOUT = 30 * 60 * 1000 // 30 minutes in milliseconds
    }

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<BaseResult<WeatherResponse>> {
        return flow {
            try {
//                val cachedWeather = weatherDao.getWeatherForCity(latitude, longitude)
//                if (cachedWeather != null &&
//                    System.currentTimeMillis() - cachedWeather.timestamp < CACHE_TIMEOUT
//                ) {
//                    // Return cached data if it's still valid
//                    emit(BaseResult.DataState(cachedWeather.toCurrentWeather()))
//                    return@flow
//                }
                val response = movieApiService.getCurrentWeather(
                    key = Constants.Authorization.API_KEY,
                    query = latitude.toString().plus(" ").plus(longitude.toString())
                )
                if (response.isSuccessful) {
                    val body = response.body()!!
//                    // Cache the new data
//                    val weatherEntity = body.toWeatherEntity(latitude)
//                    weatherDao.insertWeather(weatherEntity)
//                    // Clean up old cache entries
//                    weatherDao.deleteOldCache(System.currentTimeMillis() - CACHE_TIMEOUT)
                    emit(BaseResult.DataState(body))
                } else {
                    val errorBody = response.errorBody()?.charStream()
                    val type = object : TypeToken<WrappedErrorResponse>() {}.type
                    val errorResponse: WrappedErrorResponse =
                        Gson().fromJson(errorBody, type)
                    emit(BaseResult.ErrorState(errorResponse.errorResponse))
                }
            } catch (e: Exception) {
                // Handle exception, fallback to cached data if available
//                val cachedWeather = weatherDao.getWeatherForCity(cityName)
//                if (cachedWeather != null) {
//                    // Emit cached data if available during error
//                    emit(BaseResult.DataState(cachedWeather.toCurrentWeather()))
//                } else {
                // Emit error state if no cached data is available
                emit(BaseResult.ErrorState(errorResponse = ErrorResponse(404, "Unknown error")))
//                }
            }
        }

    }


    override suspend fun getHistory(): Flow<List<HistoryItem>> {
        return flow {
        }
    }

    override suspend fun saveWeatherHistory(weatherHistoryItem: WeatherEntity) {
        weatherDao.insertWeather(weatherHistoryItem)
    }

    override suspend fun getAllWeatherHistory(): Flow<List<WeatherEntity>> {
        return weatherDao.getAllWeatherHistory()
    }

}
