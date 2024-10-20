package com.example.myforecastapplication.presentation.ui.components.weather

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myforecastapplication.base.BaseResult
import com.example.myforecastapplication.data.remote.model.WeatherResponse
import com.example.myforecastapplication.domain.usecase.GetCityCurrentWeatherUseCase
import com.example.myforecastapplication.domain.usecase.SaveWeatherHistoryUseCase
import com.example.myforecastapplication.presentation.ui.components.camera.CameraViewModel
import com.example.myforecastapplication.utils.location.LocationManager
import com.example.myforecastapplication.utils.permissions.PermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCityCurrentWeatherUseCase: GetCityCurrentWeatherUseCase,
    private val saveWeatherHistoryUseCase: SaveWeatherHistoryUseCase,
    private val locationManager: LocationManager,
    private val permissionManager: PermissionManager,
    private val cameraViewModel: CameraViewModel,
    @ApplicationContext private val context: Context

) : ViewModel() {

    private val _weatherState =
        MutableStateFlow<GetCityCurrentWeatherState>(GetCityCurrentWeatherState.IsLoading)
    val weatherState: StateFlow<GetCityCurrentWeatherState> = _weatherState.asStateFlow()

    private val _currentCityWeather = MutableStateFlow<WeatherResponse?>(null)
    val currentCityWeather: StateFlow<WeatherResponse?> = _currentCityWeather

    private val _isCelsius = MutableStateFlow(true)
    val isCelsius: StateFlow<Boolean> = _isCelsius.asStateFlow()

    private val _capturedImage = MutableStateFlow<Bitmap?>(null)
    val capturedImage: StateFlow<Bitmap?> = _capturedImage.asStateFlow()

    init {
        observeLocationPermission()
        _capturedImage.value =
            cameraViewModel.capturedImage.value.capturedImage // Set the initial value
        observeCameraViewModel()
    }

    private fun observeCameraViewModel() {
        viewModelScope.launch {
            cameraViewModel.capturedImage.collect { cameraState ->
                _capturedImage.value = cameraState.capturedImage
                Log.d(
                    "WeatherViewModel",
                    "Captured image updated: ${cameraState.capturedImage?.hashCode()}"
                )
            }
        }
    }

    private fun observeLocationPermission() {
        viewModelScope.launch {
            permissionManager.locationPermissionFlow().take(1).collect { isGranted ->
                if (isGranted) {
                    getWeatherForCurrentLocation()
                } else {
                    _weatherState.value = GetCityCurrentWeatherState.PermissionRequired
                }
            }
        }
    }

    private fun getWeatherForCurrentLocation() {
        viewModelScope.launch {
            locationManager.getLocationUpdates()
                .take(1)  // Ensure only one location update is processed
                .flatMapLatest { location ->
                    getCityCurrentWeatherUseCase.execute(location.latitude, location.longitude)
                }.onStart {
                    _weatherState.value = GetCityCurrentWeatherState.IsLoading
                }.catch { e ->
                    _weatherState.value =
                        GetCityCurrentWeatherState.Error(e.message ?: "Unknown error occurred")
                }.collect { result ->
                    when (result) {
                        is BaseResult.DataState -> {
                            val successState = GetCityCurrentWeatherState.Success(result.items)
                            _weatherState.value = successState
                            _currentCityWeather.value = result.items
                        }

                        is BaseResult.ErrorState -> {
                            _weatherState.value = GetCityCurrentWeatherState.Error("Error")

                        }
                    }
                }
        }
    }

    fun toggleTemperatureUnit() {
        _isCelsius.value = !_isCelsius.value
    }

    fun onLocationPermissionGranted(preciseLoc: Boolean = true) {
        if (preciseLoc)
            observeLocationPermission()
        else
            _weatherState.value = GetCityCurrentWeatherState.Error("Failed to fetch weather data")
    }

    fun onLocationPermissionDenied() {
        _weatherState.value =
            GetCityCurrentWeatherState.Error("Location permission is required to fetch weather data")
    }

    fun saveWeatherData(weatherResponse: WeatherResponse) {
        viewModelScope.launch {
            Log.d(
                "WeatherViewModel",
                "Saving weather data, captured image: ${capturedImage.value?.hashCode()}"
            )
            try {
                val currentWeather =
                    (weatherState.value as? GetCityCurrentWeatherState.Success)?.data
                if (currentWeather != null) {
                    val imagePath = capturedImage.value?.let { saveImageToInternalStorage(it) }
                    saveWeatherHistoryUseCase(weatherResponse, imagePath ?: "")
                    _capturedImage.value = null // Clear the captured image after saving
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private suspend fun saveImageToInternalStorage(bitmap: Bitmap): String {
        return withContext(Dispatchers.IO) {
            val fileName = "weather_image_${System.currentTimeMillis()}.jpg"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            context.getFileStreamPath(fileName).absolutePath
        }
    }
}

sealed class GetCityCurrentWeatherState {
    object IsLoading : GetCityCurrentWeatherState()
    object PermissionRequired : GetCityCurrentWeatherState()
    data class Success(val data: WeatherResponse?) : GetCityCurrentWeatherState()
    data class Error(val message: String) : GetCityCurrentWeatherState()
}
