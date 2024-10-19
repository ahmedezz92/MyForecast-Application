package com.example.myforecastapplication.data.remote.api

import com.example.myforecastapplication.data.remote.model.ForecastResponse
import com.example.myforecastapplication.data.remote.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String,
        @Query("q") query: String,
    ): Response<WeatherResponse>
}
