package com.tta.fitnessapplication.api

import com.tta.fitnessapplication.data.model.googlefit.ResponseFit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceMap {
    @GET(ApiPath.DEMO)
    fun getDemo(
        @Query("dataTypeName") dataTypeName : String,
        @Query("timePeriod") timePeriod : String,
    ) : Call<ResponseFit>
}