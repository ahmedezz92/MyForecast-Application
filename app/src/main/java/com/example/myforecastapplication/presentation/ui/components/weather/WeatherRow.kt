package com.example.myforecastapplication.presentation.ui.components.weather

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myforecastapplication.R
import com.example.myforecastapplication.data.remote.model.WeatherResponse
import com.example.myforecastapplication.presentation.ui.components.camera.CameraScreen
import com.example.myforecastapplication.presentation.ui.components.camera.nopermission.NoPermissionScreen
import com.example.myforecastapplication.utils.Constants.URL.URL_IMAGE
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherRow(
    weather: WeatherResponse, isCelsius: Boolean, onSaveClick: (WeatherResponse) -> Unit,
    capturedImage: Bitmap?
) {

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)
    Card(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF1E2F5C))
                .padding(8.dp)
                .wrapContentHeight()
                .wrapContentSize(Alignment.Center), // Center all content inside the Column
            horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally
        ) {
            Text(
                text = weather.location.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weather.location.country, fontSize = 24.sp, color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Last updated: ${(weather.current.last_updated)}",
                fontSize = 14.sp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = URL_IMAGE.plus(weather.current.condition.icon),
                contentDescription = weather.current.condition.text,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    text = if (isCelsius) {
                        "${weather.current.temp_c}°C"
                    } else {
                        "${weather.current.temp_f}°F"
                    }, fontSize = 30.sp, color = Color.White
                )
                Text(
                    text = weather.current.condition.text, fontSize = 14.sp, color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                WeatherInfoItem(
                    "Wind", "${weather.current.wind_degree}° ${weather.current.wind_kph} km/h "
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {

                WeatherInfoItem(
                    stringResource(id = R.string.label_humidity), "${weather.current.humidity}%"
                )
                WeatherInfoItem(
                    stringResource(id = R.string.label_feels_like), "${weather.current.feelslike_c}"
                )

            }
            Button(onClick = { onSaveClick(weather) }) {
                Text(
                    text = "Save",
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            CameraContent(
                hasPermission = cameraPermissionState.status.isGranted,
                onRequestPermission = cameraPermissionState::launchPermissionRequest,
            )
            capturedImage?.let { image ->
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = "Captured Image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

    }
}

@Composable
private fun CameraContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
) {

    if (hasPermission) {
        CameraScreen()
    } else {
        NoPermissionScreen(onRequestPermission)
    }
}