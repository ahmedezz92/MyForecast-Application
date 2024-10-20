package com.example.myforecastapplication.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.impl.utils.MatrixExt.postRotate
import com.example.myforecastapplication.data.local.WeatherEntity
import com.example.myforecastapplication.data.remote.model.Condition
import com.example.myforecastapplication.data.remote.model.Current
import com.example.myforecastapplication.data.remote.model.Location
import com.example.myforecastapplication.data.remote.model.WeatherResponse

fun Bitmap.rotateBitmap(rotationDegrees: Int): Bitmap {
    val matrix = Matrix().apply {
        postRotate(-rotationDegrees.toFloat())
        postScale(-1f, -1f)
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}