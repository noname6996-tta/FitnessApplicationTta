package com.tta.fitnessapplication.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.ApiClient
import com.tta.fitnessapplication.data.model.Exercise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    val dataExercise = MutableLiveData<List<Exercise>>()
    val message = MutableLiveData<String>()
    fun getData(){
        ApiClient.API.getData().enqueue(object : Callback<List<Exercise>>{
            override fun onResponse(
                call: Call<List<Exercise>>,
                response: Response<List<Exercise>>
            ) {
                Log.e("dataExercise",response.body().toString())
                dataExercise.value = response.body()
            }

            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                Log.e("dataExercise",t.toString())
                message.value = t.toString()
            }
        })
    }
}