package com.example.myforecastapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_table WHERE cityName = :cityName")
    suspend fun getWeatherForCity(cityName: String): WeatherEntity?

    @Query("DELETE FROM weather_table WHERE cityName = :cityName")
    suspend fun deleteWeatherForCity(cityName: String)

    @Query("DELETE FROM weather_table WHERE timestamp < :timestamp")
    suspend fun deleteOldCache(timestamp: Long)
}