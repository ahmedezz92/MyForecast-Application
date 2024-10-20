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
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<BaseResult<WeatherResponse>> {
        return flow {
            try {
                val response = movieApiService.getCurrentWeather(
                    key = Constants.Authorization.API_KEY,
                    query = latitude.toString().plus(" ").plus(longitude.toString())
                )
                if (response.isSuccessful) {
                    val body = response.body()!!
                    emit(BaseResult.DataState(body))
                } else {
                    val errorBody = response.errorBody()?.charStream()
                    val type = object : TypeToken<WrappedErrorResponse>() {}.type
                    val errorResponse: WrappedErrorResponse =
                        Gson().fromJson(errorBody, type)
                    emit(BaseResult.ErrorState(errorResponse.errorResponse))
                }
            } catch (e: Exception) {
                emit(BaseResult.ErrorState(errorResponse = ErrorResponse(404, "Unknown error")))
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
