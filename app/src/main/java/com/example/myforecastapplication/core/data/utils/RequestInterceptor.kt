package com.example.myforecastapplication.core.data.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
//            .addHeader("Authorization", TOKEN_BEARER)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(newRequest)
    }

}