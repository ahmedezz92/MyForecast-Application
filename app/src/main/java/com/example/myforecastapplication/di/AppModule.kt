package com.example.myforecastapplication.di

import android.content.Context
import com.example.myforecastapplication.core.data.module.NetworkModule
import com.example.myforecastapplication.data.local.WeatherDao
import com.example.myforecastapplication.data.remote.api.WeatherApiService
import com.example.myforecastapplication.data.repository.WeatherRepositoryImp
import com.example.myforecastapplication.domain.repository.WeatherRepository
import com.example.myforecastapplication.domain.usecase.SavePhotoToGalleryUseCase
import com.example.myforecastapplication.utils.camera.CameraUtil
import com.example.myforecastapplication.utils.location.LocationManager
import com.example.myforecastapplication.utils.permissions.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideWeatherRepository(
        homeService: WeatherApiService,
        weatherDao: WeatherDao,
    ): WeatherRepository {
        return WeatherRepositoryImp(homeService, weatherDao)
    }

    @Provides
    @Singleton
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return LocationManager(context)
    }

    @Provides
    @Singleton
    fun provideCameraUtil(@ApplicationContext context: Context): CameraUtil {
        return CameraUtil(context)
    }

    @Provides
    @Singleton
    fun provideSavePhotoToGalleryUseCase(
        @ApplicationContext context: Context
    ): SavePhotoToGalleryUseCase {
        return SavePhotoToGalleryUseCase(context)
    }
    @Provides
    @Singleton
    fun providePermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManager(context)
    }
}