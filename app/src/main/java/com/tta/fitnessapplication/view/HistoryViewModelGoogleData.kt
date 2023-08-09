package com.tta.fitnessapplication.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.api.ApiClientDemo
import com.tta.fitnessapplication.data.model.googlefit.DataFit
import com.tta.fitnessapplication.data.model.googlefit.ResponseFit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModelGoogleData : ViewModel() {
    val listStepsCount = MutableLiveData<List<DataFit>>()
    val listCaloriesExpended = MutableLiveData<List<DataFit>>()
    val listHeartMinutes = MutableLiveData<List<DataFit>>()
    val listWeight = MutableLiveData<List<DataFit>>()
    val listSleepTracker = MutableLiveData<List<DataFit>>()
    val message = MutableLiveData<String>()
    fun getData() {
        ApiClientDemo.APIDEMO.getDemo(
            "steps_count,calories_expended,heart_minutes,sleep_segment,weight",
            "30days"
        ).enqueue(object : Callback<ResponseFit> {
            override fun onResponse(call: Call<ResponseFit>, response: Response<ResponseFit>) {
                listStepsCount.value = response.body()?.steps_count
                listCaloriesExpended.value = response.body()?.calories_expended
                listHeartMinutes.value = response.body()?.heart_minutes
                listSleepTracker.value = response.body()?.sleep_segment
                listWeight.value = response.body()?.weight
            }

            override fun onFailure(call: Call<ResponseFit>, t: Throwable) {
                message.value = t.toString()
            }

        })
    }
}