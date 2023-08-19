package com.tta.fitnessapplication.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Article
import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.model.UserLoginResponse
import com.tta.fitnessapplication.data.model.Video
import com.tta.fitnessapplication.data.repository.RepositoryApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repositoryApi: RepositoryApi) : ViewModel() {
    val dataExercise = MutableLiveData<Response<ResponseProfile>>()
    val login = MutableLiveData<Response<UserLoginResponse>>()
    val listHistoryByDate = MutableLiveData<Response<BaseResponse<MutableList<History>>>>()
    val listHistoryByDateAndType = MutableLiveData<Response<BaseResponse<MutableList<History>>>>()
    val createHistoryStatus = MutableLiveData<Response<BaseResponse<String>>>()
    val listVideo = MutableLiveData<List<Video>>()
    val listArticle = MutableLiveData<List<Article>>()

    fun getUserData(email: String) {
        viewModelScope.launch {
            val list = repositoryApi.getProfile(email)
            dataExercise.value = list
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            login.value = repositoryApi.login(email, password)
        }
    }

    fun register(email: String, password: String, firstname: String, lastname: String) {
        viewModelScope.launch {
            login.value = repositoryApi.register(email, password, firstname, lastname)
        }
    }

    fun getHistoryByDate(idUser: String, date: String) {
        viewModelScope.launch {
            listHistoryByDate.value = repositoryApi.getHistoryByDate(idUser, date)
        }
    }

    fun getListHistoryByDateAndType(
        idUser: String,
        date: String,
        type: String
    ) {
        viewModelScope.launch {
            listHistoryByDateAndType.value =
                repositoryApi.getListHistoryByDateAndType(idUser, date, type)
        }
    }

    fun createHistory(
        idUser: String,
        date: String,
        time: String,
        activity: String,
        type: String,
        value: String
    ) {
        viewModelScope.launch {
            createHistoryStatus.value =
                repositoryApi.createHistory(idUser, date, time, activity, type, value)
        }
    }

    fun getVideo() {
        viewModelScope.launch {
            listVideo.value = repositoryApi.getVideo().body()?.data
        }
    }

    fun getArticle() {
        viewModelScope.launch {
            listArticle.value = repositoryApi.getListArticle().body()?.data
        }
    }
}