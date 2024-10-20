package com.example.myforecastapplication.presentation.ui.components.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myforecastapplication.data.remote.model.WeatherResponse
import com.example.myforecastapplication.domain.usecase.SavePhotoToGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val savePhotoToGalleryUseCase: SavePhotoToGalleryUseCase
) : ViewModel() {

    private val _capturedImage = MutableStateFlow(CameraState())
    val capturedImage = _capturedImage.asStateFlow()

    fun storePhotoInGallery(bitmap: Bitmap) {
        viewModelScope.launch {
            savePhotoToGalleryUseCase.call(bitmap)
            updateCapturedPhotoState(bitmap)
        }
    }

    private fun updateCapturedPhotoState(updatedPhoto: Bitmap?) {
        _capturedImage.value.capturedImage?.recycle()
        _capturedImage.value = _capturedImage.value.copy(capturedImage = updatedPhoto)
    }

    override fun onCleared() {
        _capturedImage.value.capturedImage?.recycle()
        super.onCleared()
    }
}