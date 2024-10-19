package com.example.myforecastapplication.presentation.ui.components.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun WeatherInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.LightGray
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}
