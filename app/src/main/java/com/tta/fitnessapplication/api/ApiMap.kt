package com.tta.fitnessapplication.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tta.fitnessapplication.data.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiMap {
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constant.BASE_URL_MAP)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }
    val API: ApiServiceMap by lazy { retrofit.create(ApiServiceMap::class.java) }

}