package com.tta.fitnessapplication.view.activity.SleepTracker

import androidx.lifecycle.MutableLiveData
import com.tta.fitnessapplication.api.ApiClientDemo
import com.tta.fitnessapplication.data.model.googlefit.DataFit
import com.tta.fitnessapplication.data.model.googlefit.ResponseFit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SleepTrackerViewModel {
    val listSleepTracker = MutableLiveData<List<DataFit>>()
    val message = MutableLiveData<String>()
    fun getData() {
        ApiClientDemo.APIDEMO.getDemo(
            "sleep_segment",
            ""
        ).enqueue(object : Callback<ResponseFit> {
            override fun onResponse(call: Call<ResponseFit>, response: Response<ResponseFit>) {
                listSleepTracker.value = response.body()?.sleep_segment
            }

            override fun onFailure(call: Call<ResponseFit>, t: Throwable) {
                message.value = t.toString()
            }

        })
    }
}