package com.example.myforecastapplication.presentation.ui.components.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myforecastapplication.data.local.HistoryItem
import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.domain.usecase.GetHistoryUseCase
import com.example.myforecastapplication.presentation.ui.components.weather.GetCityCurrentWeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
) : ViewModel() {
    /*state*/
    private val _historyState = MutableStateFlow<List<WeatherEntity>>(emptyList())
    val historyState: StateFlow<List<WeatherEntity>> = _historyState



    init {
        loadWeatherHistory()
    }


    private fun loadWeatherHistory() {
        viewModelScope.launch {
            try {
                getHistoryUseCase.execute().onStart { }.catch { }
                    .collect {
                        _historyState.value = it
                    }

            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}


sealed class GetHistoryState {
    object IsLoading : GetHistoryState()
    data class Success(val data: List<HistoryItem>) : GetHistoryState()
    object EmptyHistory : GetHistoryState()
}