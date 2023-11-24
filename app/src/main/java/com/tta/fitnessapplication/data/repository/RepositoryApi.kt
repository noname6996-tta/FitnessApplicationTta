package com.tta.fitnessapplication.data.repository

import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.ResoinseArticle
import com.tta.fitnessapplication.data.model.ResponseCategoryFood
import com.tta.fitnessapplication.data.model.ResponseFood
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.model.ResponseRegister
import com.tta.fitnessapplication.data.model.ResponseVideo
import com.tta.fitnessapplication.data.model.UserLoginResponse
import com.tta.fitnessapplication.data.model.map.ModelMap
import com.tta.fitnessapplication.data.model.map.ResponseMap
import retrofit2.Response

class RepositoryApi {
    suspend fun getProfile(email: String): Response<ResponseProfile> {
        return ApiClient.API.getUserProfile(email)
    }

    suspend fun updateProfile(
        email: String,
        gender: String,
        age: String,
        tall: String,
        weight: String,
        firstname: String,
        lastname: String,
        progess: Int
    ): Response<BaseResponse<String>> {
        return ApiClient.API.updateUserInfo(
            email,
            gender,
            age,
            tall,
            weight,
            firstname,
            lastname,
            progess
        )
    }

    suspend fun updateUserProgess(
        email: String,
        progess: Int
    ): Response<BaseResponse<String>> {
        return ApiClient.API.updateUserProgess(email, progess)
    }

    suspend fun login(email: String, password: String): Response<UserLoginResponse> {
        return ApiClient.API.login(email, password)
    }

    suspend fun register(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ): Response<ResponseRegister> {
        return ApiClient.API.register(email, password, firstname, lastname)
    }

    suspend fun getHistoryById(
        idUser: String,
    ): Response<BaseResponse<MutableList<History>>> {
        return ApiClient.API.getHistoryById(idUser)
    }

    suspend fun uploadHistoryUser(
        id_user: String,
        date: String,
        time: String,
        activity: String,
        type: String,
        value: String
    ): Response<BaseResponse<String>> {
        return ApiClient.API.uploadHistoryUser(id_user, date, time, activity, type, value)
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

    suspend fun getListArticle(): Response<ResoinseArticle> {
        return ApiClient.API.getArticle()
    }

    suspend fun getVideo(): Response<ResponseVideo> {
        return ApiClient.API.getVideo()
    }

    suspend fun getCategoryFood(): Response<ResponseCategoryFood> {
        return ApiClient.API.getAllCategory()
    }

    suspend fun getSuggestFood(progess: Int): Response<ResponseFood> {
        return ApiClient.API.getSuggestFood(progess)
    }

    suspend fun getFoodById(id: String): Response<ResponseFood> {
        return ApiClient.API.getFoodByID(id)
    }

    suspend fun getFoodByIdCategory(id: Int): Response<ResponseFood> {
        return ApiClient.API.getFoodByCategory(id)
    }

    suspend fun getDataMap(lat: Double, lng: Double, radius: String): Response<ResponseMap> {
        return ApiClient.API.getListMap(lat, lng, radius)
    }

    suspend fun getLocation(id: Int): Response<ModelMap> {
        return ApiClient.API.getLocationInfo(id)
    }

    suspend fun getBackUpFile(email: String): Response<BaseResponse<MutableList<String>>> {
        return ApiClient.API.getBackUpFile(email)
    }

    suspend fun deleteHistory(id_user: Int): Response<BaseResponse<String>> {
        return ApiClient.API.deleteHistory(id_user)
    }

    suspend fun updateUserBackUp(
        email: String,
        backuptime: String
    ): Response<BaseResponse<String>> {
        return ApiClient.API.updateUserBackUp(email, backuptime)
    }
}