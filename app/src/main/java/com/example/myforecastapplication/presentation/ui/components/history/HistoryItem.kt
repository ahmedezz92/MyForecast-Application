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
import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.utils.Constants
import com.example.myforecastapplication.utils.Constants.URL.URL_IMAGE

@Composable
fun HistoryItem(historyItem: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFEFEFE),
        )
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFFEFEFE))
                .padding(10.dp)
                .fillMaxHeight()
                .fillMaxWidth(), // Center all content inside the Column
            verticalAlignment = Alignment.CenterVertically, // Center content horizontally
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = URL_IMAGE.plus(historyItem.conditionIcon),
                contentDescription = historyItem.conditionText,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = historyItem.cityName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = historyItem.lastUpdate,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
                Text(
                    text = historyItem.conditionText,
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${historyItem.temperatureC}°C",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${historyItem.temperatureF}°F",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

        }
    }
}