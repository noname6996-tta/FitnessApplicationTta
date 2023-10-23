package com.tta.fitnessapplication.api

import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val API: ApiService by lazy { retrofit.create(ApiService::class.java) }

}