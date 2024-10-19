package com.example.myforecastapplication.presentation.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.myforecastapplication.presentation.ui.components.weather.GetCityCurrentWeatherState
import com.example.myforecastapplication.presentation.ui.components.weather.WeatherViewModel
import com.example.myforecastapplication.presentation.ui.screens.ForecastApp
import com.example.myforecastapplication.utils.permissions.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                viewModel.onLocationPermissionGranted()
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                viewModel.onLocationPermissionGranted(preciseLoc = false)
            }

            else -> {
                // No location access granted.
                viewModel.onLocationPermissionDenied()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ForecastApp(
                    onLocationRequestPermission = { requestLocationPermission() },
                )
            }
        }
    }


    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(PermissionManager.LOCATION_PERMISSIONS)
    }

}