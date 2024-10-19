package com.example.myforecastapplication.presentation.ui.components.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myforecastapplication.R
import com.example.myforecastapplication.data.local.HistoryItem
import com.example.myforecastapplication.utils.Constants

@Composable
fun HistoryItem(historyItem: HistoryItem) {
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
                .padding(16.dp)
                .fillMaxHeight()
                .wrapContentSize(Alignment.Center), // Center all content inside the Column
            horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally
        ) {
            Text(
                text = historyItem.locationName,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = historyItem.date,
                fontSize = 24.sp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = Constants.URL.URL_IMAGE.plus(historyItem.conditionIcon),
                contentDescription = null,
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
                    text = "${historyItem.temperature}°C",
                    fontSize = 30.sp,
                    color = Color.White
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
//                WeatherInfoItem(
//                    "Wind",
//                    "${historyItem.windDegree}° ${historyItem.windSpeed} km/h "
//                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {

//                WeatherInfoItem(
//                    stringResource(id = R.string.label_humidity),
//                    "${historyItem.humidity}%"
//                )
//                WeatherInfoItem(
//                    stringResource(id = R.string.label_feels_like),
//                    historyItem.feelsLike
//                )

            }

        }
    }
}