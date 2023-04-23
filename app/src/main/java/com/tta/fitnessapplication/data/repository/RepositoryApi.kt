package com.tta.fitnessapplication.data.repository

import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.data.model.ResponseProfile
import retrofit2.Response

class RepositoryApi {
    suspend fun getData() : Response<List<Exercise>> {
        return ApiClient.API.getData()
    }

    suspend fun getProfile(email: String) : Response<ResponseProfile> {
        return ApiClient.API.getUserProfile(email)
    }
}