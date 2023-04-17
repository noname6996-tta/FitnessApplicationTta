package com.tta.fitnessapplication.view.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.api.ApiClientDemo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    fun getData(){
        ApiClientDemo.APIDEMO.getDemo("steps_count").enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("tta", response.body()?.toString()!!)
                Log.e("tta", call.request().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("tta", t.toString())
            }

        })
    }
}