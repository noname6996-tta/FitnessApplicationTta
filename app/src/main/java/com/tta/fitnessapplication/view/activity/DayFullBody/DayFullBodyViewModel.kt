package com.tta.fitnessapplication.view.activity.DayFullBody

import androidx.lifecycle.MutableLiveData
import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.data.model.Fullbody
import com.tta.fitnessapplication.data.model.ResponseFullBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DayFullBodyViewModel {
    val message = MutableLiveData<String>()
    val dataFullBody = MutableLiveData<List<Fullbody>>()
    var dataDayFullBody = MutableLiveData<Fullbody>()
    var dataExercise = MutableLiveData<Exercise>()
    fun getDataFullBody(){
        ApiClient.API.getFullBody().enqueue(object : Callback<ResponseFullBody>{
            override fun onResponse(
                call: Call<ResponseFullBody>,
                response: Response<ResponseFullBody>
            ) {
                if (response.body()?.response==1){
                    dataFullBody.value = response.body()?.data
                } else {
                    message.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseFullBody>, t: Throwable) {
                message.value = t.toString()
            }

        })
    }

    fun getDayFullBody(id : String){
        ApiClient.API.getDayFullBody(id).enqueue(object : Callback<Fullbody>{
            override fun onResponse(
                call: Call<Fullbody>,
                response: Response<Fullbody>
            ) {
                dataDayFullBody.value = response.body()
            }

            override fun onFailure(call: Call<Fullbody>, t: Throwable) {
                message.value = t.toString()
            }

        })
    }

    fun getExercise(id : String){
        ApiClient.API.getExercise(id).enqueue(object : Callback<Exercise>{
            override fun onResponse(call: Call<Exercise>, response: Response<Exercise>) {
                dataExercise.value = response.body()
            }

            override fun onFailure(call: Call<Exercise>, t: Throwable) {
                message.value = t.toString()
            }

        })
    }


}