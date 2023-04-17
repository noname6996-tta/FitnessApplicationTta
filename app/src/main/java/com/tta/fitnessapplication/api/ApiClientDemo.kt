package com.tta.fitnessapplication.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL
import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL_DEMO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientDemo {
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL_DEMO)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }
    val APIDEMO: ApiServiceDemo by lazy { retrofit.create(ApiServiceDemo::class.java) }

}