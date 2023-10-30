package com.tta.fitnessapplication.api

import com.tta.fitnessapplication.data.model.googlefit.ResponseFit
import com.tta.fitnessapplication.data.model.map.Location
import com.tta.fitnessapplication.data.model.map.ModelMap
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceMap {
    @GET(ApiPath.MAP_API)
    suspend fun mapApi(
        @Query("location") location : String,
        @Query("radius") radius : String,
        @Query("type") type : String,
        @Query("key") key : String
    ) : Response<ModelMap>
}