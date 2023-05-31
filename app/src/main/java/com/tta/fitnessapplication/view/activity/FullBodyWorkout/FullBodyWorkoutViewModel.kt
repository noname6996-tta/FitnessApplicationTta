package com.tta.fitnessapplication.view.activity.FullBodyWorkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.ResponseTool
import com.tta.fitnessapplication.data.model.Tool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullBodyWorkoutViewModel : ViewModel() {
    val toolList = MutableLiveData<List<Tool>>()
    val message = MutableLiveData<String>()

    fun getToolList(){
        ApiClient.API.getTool().enqueue(object : Callback<ResponseTool>{
            override fun onResponse(call: Call<ResponseTool>, response: Response<ResponseTool>) {
                if (response.body()?.response==1){
                    toolList.value = response.body()?.data
                } else {
                    message.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseTool>, t: Throwable) {
                message.postValue(t.toString())
            }

        })
    }
}