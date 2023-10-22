package com.tta.fitnessapplication.view.activity.workout.DayFullBody

import android.util.Log
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
    var dataDayFullBody = MutableLiveData<Fullbody>()
    var dataExercise = MutableLiveData<List<Exercise>>()

    fun getDayFullBody(id: String) {
        ApiClient.API.getDayFullBody(id).enqueue(object : Callback<ResponseFullBody> {
            override fun onResponse(
                call: Call<ResponseFullBody>,
                response: Response<ResponseFullBody>
            ) {
                if (response.body()?.response == 1) {
                    dataDayFullBody.value = response.body()?.fullbody
                    dataExercise.value = response.body()?.data
                } else {
                    message.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseFullBody>, t: Throwable) {
                message.value = t.toString()
            }

        })
    }
}