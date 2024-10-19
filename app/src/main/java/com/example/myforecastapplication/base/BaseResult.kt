package com.example.myforecastapplication.base

import com.example.myforecastapplication.core.data.utils.ErrorResponse

sealed class BaseResult<out T> {
    data class DataState<T : Any>(val items: T?) : BaseResult<T>()
    data class ErrorState(val errorResponse: ErrorResponse?) : BaseResult<Nothing>()
}