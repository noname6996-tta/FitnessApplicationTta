package com.tta.fitnessapplication.view.activity.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.UserLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(){
    val success = MutableLiveData<Int>()
    val message = MutableLiveData<String>()
    fun register(email: String,password: String,firstname: String,lastname: String)
    {
        ApiClient.API.register(email, password, firstname, lastname).enqueue(object :
            Callback<UserLoginResponse> {
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                Log.e("tta",call.request().toString())
                Log.e("tta",response.body().toString())
                if (response.body()?.success==1){
                    success.value = response.body()?.success
                    message.value = response.body()?.message
                } else {
                    message.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                message.value = t.toString()
                Log.e("tta",call.request().toString())
                Log.e("tta",t.toString())
            }

        })
    }
}