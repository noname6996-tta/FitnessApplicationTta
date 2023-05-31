package com.tta.fitnessapplication.api

import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.data.model.Fullbody
import com.tta.fitnessapplication.data.model.ResoinseArticle
import com.tta.fitnessapplication.data.model.ResponseExercise
import com.tta.fitnessapplication.data.model.ResponseFullBody
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.model.ResponseTool
import com.tta.fitnessapplication.data.model.ResponseVideo
import com.tta.fitnessapplication.data.model.UserLoginResponse
import com.tta.fitnessapplication.data.model.Video
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(ApiPath.GETDATA)
    suspend fun getData(): Response<List<Exercise>>

    @POST(ApiPath.LOGIN)
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<UserLoginResponse>

    @POST(ApiPath.INSERT_USER)
    fun register(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("firstname") firstname: String,
        @Query("lastname") lastname: String
    ): Call<UserLoginResponse>

    @POST(ApiPath.USER_INFO)
    suspend fun getUserProfile(
        @Query("email") email: String,
    ): Response<ResponseProfile>

    @GET(ApiPath.FULLBODY)
    fun getFullBody(): Call<ResponseFullBody>

    @POST(ApiPath.SELECT_FULLBODY)
    fun getDayFullBody(
        @Query("id") id: String,
    ): Call<Fullbody>

    @GET(ApiPath.EXERCISE)
    fun getALlExercise(): Call<ResponseExercise>

    @POST(ApiPath.SELECT_EXERCISE)
    fun getExercise(
        @Query("id") id: String,
    ): Call<Exercise>

    @GET(ApiPath.VIDEO)
    fun getVideo(): Call<ResponseVideo>

    @GET(ApiPath.ARTICLE)
    fun getArticle(): Call<ResoinseArticle>

    @GET(ApiPath.TOOL)
    fun getTool(): Call<ResponseTool>
}

//    @POST(ApiPath.LOGIN)
//    fun login(
//        @Query("username") username: String,
//        @Query("password") password: String
//    ): Call<UserLoginResponse>
//
//    @Multipart
//    @POST(ApiPath.UPDATE_USER)
//    fun updateUser(
//        @Header("Authorization") token: String,
//        @Query("name") username: String,
//        @Query("password") password: String,
//        @Part image: MultipartBody.Part?
//    ): Call<BaseResponse>
//
//    @POST(ApiPath.UPDATE_USER)
//    fun updateUserVersion2(
//        @Header("Authorization") token: String,
//        @Query("name") username: String,
//        @Query("password") password: String,
//    ): Call<BaseResponse>
//
//    @POST(ApiPath.STORE_LIST)
//    fun storeList(
//        @Header("Authorization") token: String,
//        @Query("radius_km") radius_km: Int,
//        @Query("username") username: String,
//        @Query("lat") lat: String,
//        @Query("lng") lng: String
//    ): Call<RoadResponse>
//
//    @POST(ApiPath.LOCATION_HISTORY)
//    fun history(
//        @Header("Authorization") token: String,
//        @Query("username") username: String,
//        @Query("page") page: Int,
//        @Query("date_checkin") date_checkin: String
//    ): Call<HistoryResponse>
//
//    @POST(ApiPath.USER_LIST)
//    fun admin(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//    ): Call<AdminResponse>
//
//    @Multipart
//    @POST(ApiPath.ADD_STORE)
//    fun addStore(
//        @Header("Authorization") token: String,
//        @Part image: List<MultipartBody.Part>,
//        @Query("username") username: String,
//        @Query("address") address: String,
//        @Query("lat") lat: String,
//        @Query("lng") lng: String,
//        @Query("store_name") store_name: String,
//        @Query("customer_name") customer_name: String,
//        @Query("customer_phone") customer_phone: String,
//    ): Call<AddStoreResponse>
//
//    @GET(ApiPath.STORE_ADDED)
//    fun addedStore(@Header("Authorization") token: String): Call<AddedStoreResponse>
//    @GET(ApiPath.PROFILE)
//    fun getUserInfo(@Header("Authorization") token: String): Call<UserLoginResponse>
//    @GET(ApiPath.STORE_DETAIL)
//    fun getStoreDetail(
//        @Header("Authorization") token: String,
//        @Path(value = "id") id: Int
//    ): Call<StoreDetailResponse>
//
//    @Multipart
//    @POST(ApiPath.CHECK_IN)
//    fun checkIn(
//        @Header("Authorization") token: String,
//        @Query("username") username: String,
//        @Part image: MultipartBody.Part,
//        @Query("store_id") store_id: Int,
//        @Query("status") status: Int,
//        @Query("lat") lat: String,
//        @Query("lng") lng: String,
//        @Query("note") note: String,
//    ): Call<AddStoreResponse>
//
//    @POST(ApiPath.STORE_UPDATE)
//    fun updateStore(
//        @Header("Authorization") token: String,
//        @Path(value = "id") id: Int,
//        @Query("address") address: String,
//        @Query("store_name") store_name: String,
//        @Query("customer_name") customer_name: String,
//        @Query("customer_phone") customer_phone: String,
//    ): Call<UpdateStore>
//
//    @Multipart
//    @POST(ApiPath.STORE_IMAGE_CREATE)
//    fun createStoreImage(
//        @Header("Authorization") token: String,
//        @Part image: MultipartBody.Part,
//        @Query("store_id") store_id: Int
//    ): Call<Image>
//
//    @DELETE(ApiPath.STORE_IMAGE_DELETE)
//    fun deleteStoreImage(
//        @Header("Authorization") token: String,
//        @Path(value = "id") id: Int,
//    ): Call<Image>