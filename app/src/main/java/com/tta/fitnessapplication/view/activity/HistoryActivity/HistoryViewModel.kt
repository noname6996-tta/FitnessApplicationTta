package com.tta.fitnessapplication.view.activity.HistoryActivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.Api
import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.History
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    val message = MutableLiveData<String>()
    val list = MutableLiveData<MutableList<History>?>()

    val isDone = MutableLiveData<Boolean>()

    fun getListHistoryByDate(idUser: String, date: String) {
        ApiClient.API.getHistoryByDate(idUser, date)
            .enqueue(object : Callback<BaseResponse<MutableList<History>>> {
                override fun onResponse(
                    call: Call<BaseResponse<MutableList<History>>>,
                    response: Response<BaseResponse<MutableList<History>>>
                ) {
                    Log.e("getListHistoryByDate", call.request().toString())
                    if (response.body()?.response == 0) {
                        message.value = "Không có dữ liệu"
                    } else {
                        list.value = response.body()?.data
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<MutableList<History>>>,
                    t: Throwable
                ) {
                    message.value = t.toString()
                    Log.e("getListHistoryByDate", call.request().toString())
                }
            })
    }

    fun getListHistoryByDateAndTypr(idUser: String, date: String, type: String) {
        ApiClient.API.getHistoryByDateAndType(idUser, date, type)
            .enqueue(object : Callback<BaseResponse<MutableList<History>>> {
                override fun onResponse(
                    call: Call<BaseResponse<MutableList<History>>>,
                    response: Response<BaseResponse<MutableList<History>>>
                ) {
                    Log.e("getListHistoryByDate", call.request().toString())
                    if (response.body()?.response == 0) {
                        message.value = "Không có dữ liệu"
                    } else {
                        list.value = response.body()?.data
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<MutableList<History>>>,
                    t: Throwable
                ) {
                    message.value = t.toString()
                    Log.e("getListHistoryByDate", call.request().toString())
                }
            })
    }

    fun createHistory(
        idUser: String,
        date: String,
        time: String,
        activity: String,
        type: String,
        value: String
    ) {
        ApiClient.API.createHistory(idUser, date, time, activity, type, value)
            .enqueue(object : Callback<BaseResponse<String>> {
                override fun onResponse(
                    call: Call<BaseResponse<String>>,
                    response: Response<BaseResponse<String>>
                ) {
                    if (response.body()?.response == 1) {
                        isDone.value = true
                    } else {
                        isDone.value = false
                        message.value = "Something went wrong !"
                    }
                }

                override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                    message.value = t.toString()
                }

            })
    }
}