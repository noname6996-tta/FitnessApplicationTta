package com.tta.fitnessapplication.api

import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.ResoinseArticle
import com.tta.fitnessapplication.data.model.ResponseExercise
import com.tta.fitnessapplication.data.model.ResponseFullBody
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.model.ResponseTool
import com.tta.fitnessapplication.data.model.ResponseVideo
import com.tta.fitnessapplication.data.model.UserLoginResponse
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
    ): Response<UserLoginResponse>

    @POST(ApiPath.USER_INFO)
    suspend fun getUserProfile(
        @Query("email") email: String,
    ): Response<ResponseProfile>

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

    @POST(ApiPath.HISTORY_BY_DATE)
    suspend fun getHistoryByDate(
        @Query("id_user") id_user: String,
        @Query("date") date: String
    ): Response<BaseResponse<MutableList<History>>>

    @POST(ApiPath.HISTORY_BY_DATE_AND_TYPE)
    suspend fun getHistoryByDateAndType(
        @Query("id_user") id_user: String,
        @Query("date") date: String,
        @Query("type") type: String
    ): Response<BaseResponse<MutableList<History>>>

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
}