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

    fun getUserData(email: String){
        viewModelScope.launch {
            val list = repositoryApi.getProfile(email)
            dataExercise.value = list
        }
    }
}