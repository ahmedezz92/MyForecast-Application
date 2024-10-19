package com.example.myforecastapplication.presentation.ui.components.history

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myforecastapplication.data.local.HistoryItem
import com.example.myforecastapplication.domain.usecase.GetHistoryUseCase
import com.example.myforecastapplication.presentation.ui.components.weather.GetCityCurrentWeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
) : ViewModel() {
    /*state*/
    private val _historyState = MutableStateFlow<GetHistoryState>(GetHistoryState.IsLoading)
    val historyState: StateFlow<GetHistoryState> = _historyState

    private val _historyItems = MutableStateFlow<List<HistoryItem>>(emptyList())
    val historyItems: StateFlow<List<HistoryItem>> = _historyItems

    /*loading progress for loading state*/
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private fun setLoading() {
        _isLoading.value = true
    }

    private fun hideLoading() {
        _isLoading.value = false
    }

    init {
        loadHistoryItems()
    }


    private fun loadHistoryItems(): Flow<GetCityCurrentWeatherState> = flow {
        getHistoryUseCase.execute().onStart {
            setLoading()
        }
            .catch { exception ->
                hideLoading()
                Log.e("exp", exception.message.toString())

            }
            .collect { result ->
                hideLoading()
                if (result.isEmpty()) {
                    val emptyState = GetHistoryState.EmptyHistory
                    _historyState.value = emptyState

                } else {
                    val successState = GetHistoryState.Success(result)
                    _historyState.value = successState
                    _historyItems.value = result
                }
            }
    }

    fun refreshHistory() {
        loadHistoryItems()
    }
}

sealed class GetHistoryState {
    object IsLoading : GetHistoryState()
    data class Success(val data: List<HistoryItem>) : GetHistoryState()
    object EmptyHistory : GetHistoryState()
}