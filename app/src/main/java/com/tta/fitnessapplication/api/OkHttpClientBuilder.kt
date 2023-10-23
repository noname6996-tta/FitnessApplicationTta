package com.tta.fitnessapplication.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientBuilder {
    fun build(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.SECONDS)
            .writeTimeout(5000, TimeUnit.SECONDS)
            .readTimeout(5000, TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(interceptor)
            .build()
    }

}