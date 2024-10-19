package com.example.myforecastapplication.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.lifecycleScope
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
    ) { /* Handle permission result if needed */ }

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