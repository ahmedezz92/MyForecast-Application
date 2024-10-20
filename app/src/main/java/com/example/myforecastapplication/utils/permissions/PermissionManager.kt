package com.example.myforecastapplication.utils.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PermissionManager @Inject constructor(private val context: Context) {

    fun locationPermissionFlow(): Flow<Boolean> = flow {
        while (true) {
            emit(hasLocationPermission())
            kotlinx.coroutines.delay(1000)
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    }
}