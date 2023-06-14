package com.tta.fitnessapplication.view.activity.HistoryActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.History
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    val message = MutableLiveData<String>()
    val list = MutableLiveData<MutableList<History>?>()

    fun getListHistoryByDate(idUser : Int, date : String){
        ApiClient.API.getHistoryByDate(idUser,date).enqueue(object : Callback<BaseResponse<MutableList<History>>>{
            override fun onResponse(
                call: Call<BaseResponse<MutableList<History>>>,
                response: Response<BaseResponse<MutableList<History>>>
            ) {
                if (response.body()?.response==0){
                    message.value = "Có lỗi xảy ra"
                } else {
                    list.value = response.body()?.data
                }
            }

            override fun onFailure(call: Call<BaseResponse<MutableList<History>>>, t: Throwable) {
                message.value = t.toString()
            }
        })
    }
}