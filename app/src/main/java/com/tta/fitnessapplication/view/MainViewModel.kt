package com.tta.fitnessapplication.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.repository.RepositoryApi
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repositoryApi: RepositoryApi): ViewModel() {
    val dataExercise = MutableLiveData<Response<ResponseProfile>>()
    val message = MutableLiveData<String>()
//    fun getData(){
//        ApiClient.API.getData().enqueue(object : Callback<List<Exercise>>{
//            override fun onResponse(
//                call: Call<List<Exercise>>,
//                response: Response<List<Exercise>>
//            ) {
//                Log.e("dataExercise",response.body().toString())
//                dataExercise.value = response.body()
//            }
//
//            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
//                Log.e("dataExercise",t.toString())
//                message.value = t.toString()
//            }
//        })
//    }

    fun getUserData(email: String){
        viewModelScope.launch {
            val list = repositoryApi.getProfile(email)
            dataExercise.value = list
        }
    }
}