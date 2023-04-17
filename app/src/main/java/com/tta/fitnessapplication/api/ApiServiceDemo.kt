package com.tta.fitnessapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceDemo {
    @GET(ApiPath.DEMO)
    fun getDemo(
        @Query("dataTypeName") dataTypeName : String
    ) : Call<String>
}