package com.example.myforecastapplication.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myforecastapplication.presentation.ui.components.camera.CameraViewModel
import com.example.myforecastapplication.presentation.ui.components.history.HistoryList
import com.example.myforecastapplication.presentation.ui.components.history.HistoryViewModel
import com.example.myforecastapplication.presentation.ui.components.weather.WeatherScreen
import com.example.myforecastapplication.presentation.ui.components.weather.WeatherViewModel

@Composable
fun ForecastApp(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel(),
    onLocationRequestPermission: () -> Unit,
) {
    val navController = rememberNavController()

    Scaffold { padding ->
        NavHost(
            navController = navController,
            startDestination = "HistoryScreen",
            modifier = Modifier.padding(padding)
        ) {
            composable("HistoryScreen") {
                HistoryList(
                    navController = navController,
                    viewModel = historyViewModel
                )
            }
            composable("WeatherScreen") {
                WeatherScreen(
                    navController = navController, weatherViewModel = weatherViewModel,
                    onLocationRequestPermission = onLocationRequestPermission,
                )
            }
        }
    }
}



