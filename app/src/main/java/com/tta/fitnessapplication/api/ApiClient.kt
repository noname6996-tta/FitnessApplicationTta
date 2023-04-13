package com.tta.fitnessapplication.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }
    val API: ApiService by lazy { retrofit.create(ApiService::class.java) }

}