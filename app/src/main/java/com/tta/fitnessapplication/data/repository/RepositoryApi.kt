package com.tta.fitnessapplication.data.repository

import com.tta.fitnessapplication.ApiClient
import com.tta.fitnessapplication.data.model.Exercise
import retrofit2.Response

class RepositoryApi {
    suspend fun getData() : Response<List<Exercise>> {
        return ApiClient.API.getData()
    }
}