package com.tta.fitnessapplication.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val API: ApiService by lazy { retrofit.create(ApiService::class.java) }

}