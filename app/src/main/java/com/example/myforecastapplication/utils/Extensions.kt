package com.example.myforecastapplication.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.impl.utils.MatrixExt.postRotate
import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.data.remote.model.Condition
import com.example.myforecastapplication.data.remote.model.Current
import com.example.myforecastapplication.data.remote.model.Location
import com.example.myforecastapplication.data.remote.model.WeatherResponse

fun WeatherEntity.toCurrentWeather(): WeatherResponse {
    return WeatherResponse(
        location = Location(
            name = this.cityName, // The city name stored in the entity
            country = this.country,
            region = this.region
        ),
        current = Current(
            temp_c = this.temperatureC,
            temp_f = this.temperatureC,
            condition = Condition(
                text = this.conditionText,
                icon = this.conditionIcon
            ),
            wind_degree = this.windDegree,
            wind_kph = this.windSpeed,
            humidity = this.humidity,
            last_updated = this.lastUpdate,
            feelslike_c = this.feelslike
        )


    )
}

fun WeatherResponse.toWeatherEntity(cityName: String): WeatherEntity {
    return WeatherEntity(
        cityName = cityName,
        country = this.location.country,
        region = this.location.region,
        temperatureC = this.current.temp_c,
        temperatureF = this.current.temp_f,
        conditionText = this.current.condition.text,
        conditionIcon = this.current.condition.icon,
        windDegree = this.current.wind_degree,
        windSpeed = this.current.wind_kph,
        humidity = this.current.humidity,
        lastUpdate = this.current.last_updated,
        feelslike = this.current.feelslike_c
    )
}

fun Bitmap.rotateBitmap(rotationDegrees: Int): Bitmap {
    val matrix = Matrix().apply {
        postRotate(-rotationDegrees.toFloat())
        postScale(-1f, -1f)
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}