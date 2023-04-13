package com.tta.fitnessapplication.view.activity.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.UserLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val checkLogin = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    fun login(email: String, password: String) {
        ApiClient.API.login(email, password).enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                if (response.body()?.success == 1) {
                    checkLogin.value = true
                } else {
                    checkLogin.value = true
                    message.value = response.body()?.message.toString()
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                message.value = t.toString()
            }
        })
    }
}