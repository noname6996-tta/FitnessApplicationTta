package com.tta.fitnessapplication.data.repository

import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.ResoinseArticle
import com.tta.fitnessapplication.data.model.ResponseCategoryFood
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.model.ResponseVideo
import com.tta.fitnessapplication.data.model.UserLoginResponse
import retrofit2.Response

class RepositoryApi {
    suspend fun getProfile(email: String): Response<ResponseProfile> {
        return ApiClient.API.getUserProfile(email)
    }

    suspend fun login(email: String, password: String): Response<UserLoginResponse> {
        return ApiClient.API.login(email, password)
    }

    suspend fun register(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ): Response<UserLoginResponse> {
        return ApiClient.API.register(email, password, firstname, lastname)
    }

    suspend fun getHistoryByDate(
        idUser: String,
        date: String
    ): Response<BaseResponse<MutableList<History>>> {
        return ApiClient.API.getHistoryByDate(idUser, date)
    }

    suspend fun getListHistoryByDateAndType(
        idUser: String,
        date: String,
        type: String
    ): Response<BaseResponse<MutableList<History>>> {
        return ApiClient.API.getHistoryByDateAndType(idUser, date, type)
    }

    suspend fun createHistory(
        idUser: String,
        date: String,
        time: String,
        activity: String,
        type: String,
        value: String
    ): Response<BaseResponse<String>> {
        return ApiClient.API.createHistory(idUser, date, time, activity, type, value)
    }

    suspend fun getListArticle():  Response<ResoinseArticle>{
        return ApiClient.API.getArticle()
    }
    suspend fun getVideo():  Response<ResponseVideo>{
        return ApiClient.API.getVideo()
    }

    suspend fun getCategoryFood():  Response<ResponseCategoryFood>{
        return ApiClient.API.getAllCategory()
    }
}