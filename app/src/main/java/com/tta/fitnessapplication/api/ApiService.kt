package com.tta.fitnessapplication.api

import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.ResoinseArticle
import com.tta.fitnessapplication.data.model.ResponseCategoryFood
import com.tta.fitnessapplication.data.model.ResponseExercise
import com.tta.fitnessapplication.data.model.ResponseFood
import com.tta.fitnessapplication.data.model.ResponseFullBody
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.model.ResponseRegister
import com.tta.fitnessapplication.data.model.ResponseTool
import com.tta.fitnessapplication.data.model.ResponseVideo
import com.tta.fitnessapplication.data.model.UserLoginResponse
import com.tta.fitnessapplication.data.model.map.ModelMap
import com.tta.fitnessapplication.data.model.map.ResponseMap
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
//    @POST(ApiPath.LOGIN)
//    fun login(
//        @Query("email") email: String,
//        @Query("password") password: String
//    ): Call<UserLoginResponse>

    @POST(ApiPath.LOGIN)
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<UserLoginResponse>

    @POST(ApiPath.INSERT_USER)
    suspend fun register(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("firstname") firstname: String,
        @Query("lastname") lastname: String
    ): Response<ResponseRegister>

    @POST(ApiPath.USER_INFO)
    suspend fun getUserProfile(
        @Query("email") email: String,
    ): Response<ResponseProfile>

    @POST(ApiPath.UPDATE_USER)
    suspend fun updateUserInfo(
        @Query("email") email: String,
        @Query("gender") gender: String,
        @Query("age") age: String,
        @Query("tall") tall: String,
        @Query("weight") weight: String,
        @Query("firstname") firstname: String,
        @Query("lastname") lastname: String,
        @Query("progess") progess: Int
    ): Response<BaseResponse<String>>

    @POST(ApiPath.UPDATE_USER_PROGESS)
    suspend fun updateUserProgess(
        @Query("email") email: String,
        @Query("progess") progess: Int
    ): Response<BaseResponse<String>>

    @GET(ApiPath.FULLBODY)
    fun getFullBody(): Call<ResponseFullBody>

    @POST(ApiPath.SELECT_FULLBODY)
    fun getDayFullBody(
        @Query("id") id: String,
    ): Call<ResponseFullBody>

    @GET(ApiPath.EXERCISE)
    fun getALlExercise(): Call<ResponseExercise>

    @POST(ApiPath.SELECT_EXERCISE)
    fun getExercise(
        @Query("id") id: String,
    ): Call<Exercise>

    @GET(ApiPath.VIDEO)
    suspend fun getVideo(): Response<ResponseVideo>

    @GET(ApiPath.ARTICLE)
    suspend fun getArticle(): Response<ResoinseArticle>

    @GET(ApiPath.TOOL)
    fun getTool(): Call<ResponseTool>

    @POST(ApiPath.HISTORY_DOWNLOAD)
    suspend fun getHistoryById(
        @Query("id_user") id_user: String,
    ): Response<BaseResponse<MutableList<History>>>

    @POST(ApiPath.HISTORY_UPLOAD)
    suspend fun uploadHistoryUser(
        @Query("id_user") id_user: String,
        @Query("date") date: String,
        @Query("time") time: String,
        @Query("activity") activity: String,
        @Query("type") type: String,
        @Query("value") value: String
    ): Response<BaseResponse<String>>

    @POST(ApiPath.HISTORY_UPLOAD)
     fun uploadHistoryUserVer2(
        @Query("id_user") id_user: String,
        @Query("date") date: String,
        @Query("time") time: String,
        @Query("activity") activity: String,
        @Query("type") type: String,
        @Query("value") value: String
    ): Call<BaseResponse<String>>

    @POST(ApiPath.INSERT_HISTORY)
    suspend fun createHistory(
        @Query("id_user") id_user: String,
//      yyyy-mm-dd
        @Query("date") date: String,
//      hh:mm:ss
        @Query("time") time: String,
        @Query("activity") activity: String,
        @Query("type") type: String,
        @Query("value") value: String,
    ): Response<BaseResponse<String>>

    @GET(ApiPath.CATEGORY_FOOD)
    suspend fun getAllCategory(): Response<ResponseCategoryFood>

    @POST(ApiPath.SUGGEST_FOOD)
    suspend fun getSuggestFood(
        @Query("progress") progress: Int
    ): Response<ResponseFood>

    @POST(ApiPath.FOOD_BY_TYPE)
    suspend fun getFoodByType(
        @Query("type") type: String
    ): Response<ResponseFood>

    @POST(ApiPath.FOOD_BY_CATEGORY)
    suspend fun getFoodByCategory(
        @Query("id") id: Int
    ): Response<ResponseFood>

    @POST(ApiPath.FOOD_BY_ID)
    suspend fun getFoodByID(
        @Query("id") id: String
    ): Response<ResponseFood>

    @POST(ApiPath.MAP_API)
    suspend fun getListMap(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: String,
    ): Response<ResponseMap>

    @POST(ApiPath.LOCATION)
    suspend fun getLocationInfo(
        @Query("id") id: Int,
    ): Response<ModelMap>

    @POST(ApiPath.BACK_UP)
    suspend fun getBackUpFile(
        @Query("email") email: String,
    ): Response<BaseResponse<String>>

    @POST(ApiPath.DELETE_HISTORY)
    suspend fun deleteHistory(
        @Query("id_user") id_user: Int,
    ): Response<BaseResponse<String>>
    @POST(ApiPath.USER_BACK_UP)
    suspend fun updateUserBackUp(
        @Query("email") email: String,
        @Query("backuptime") backuptime: String,
    ): Response<BaseResponse<String>>
    @POST(ApiPath.SEARCH_FOOD)
    suspend fun searchFood(
        @Query("name") name: String,
    ): Response<ResponseFood>
}