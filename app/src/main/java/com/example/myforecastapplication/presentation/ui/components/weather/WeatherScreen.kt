package com.example.myforecastapplication.presentation.ui.components.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myforecastapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
    onLocationRequestPermission: () -> Unit,
) {
    val weatherState by weatherViewModel.weatherState.collectAsState()
    val isCelsius by weatherViewModel.isCelsius.collectAsState()
    val weatherData by weatherViewModel.currentCityWeather.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.label_current_forecast)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (weatherState) {
                is GetCityCurrentWeatherState.IsLoading -> CircularProgressIndicator()
                is GetCityCurrentWeatherState.Error -> Text("Error:")
                is GetCityCurrentWeatherState.PermissionRequired -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Location permission is required to fetch weather data.")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onLocationRequestPermission) {
                            Text("Grant Permission")
                        }
                    }
                }


                is GetCityCurrentWeatherState.Success -> {

                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { weatherViewModel.toggleTemperatureUnit() }) {
                            Text(
                                if (isCelsius) stringResource(id = R.string.label_switch_to_f) else stringResource(
                                    id = R.string.labe_switch_to_c
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    weatherData?.let { WeatherRow(weather = it, isCelsius) }
                }

            }
        }
    }

}