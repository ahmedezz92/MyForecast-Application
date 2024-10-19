package com.example.myforecastapplication.presentation.ui.components.history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryList(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val historyItems by viewModel.historyItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather History") },
                actions = {
                    IconButton(onClick = { navController.navigate("forecast") }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add a new Forecast"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(historyItems) { item ->
                HistoryItem(item)
            }
        }
    }
}