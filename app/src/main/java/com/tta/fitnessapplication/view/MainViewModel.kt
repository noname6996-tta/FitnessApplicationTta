package com.tta.fitnessapplication.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Article
import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.CategoryFood
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.ResponseFood
import com.tta.fitnessapplication.data.model.ResponseProfile
import com.tta.fitnessapplication.data.model.ResponseRegister
import com.tta.fitnessapplication.data.model.UserLoginResponse
import com.tta.fitnessapplication.data.model.Video
import com.tta.fitnessapplication.data.model.map.ModelMap
import com.tta.fitnessapplication.data.repository.RepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel(private val repositoryApi: RepositoryApi) : ViewModel() {
    val error = MutableLiveData<String>()
    val dataExercise = MutableLiveData<Response<ResponseProfile>>()
    val updateUser = MutableLiveData<Response<BaseResponse<String>>>()
    val login = MutableLiveData<Response<UserLoginResponse>>()
    val register = MutableLiveData<Response<ResponseRegister>>()
    val downloadHistory = MutableLiveData<Response<BaseResponse<MutableList<History>>>>()
    val uploadHistory = MutableLiveData<Response<BaseResponse<String>>>()
    val createHistoryStatus = MutableLiveData<Response<BaseResponse<String>>>()
    val listVideo = MutableLiveData<List<Video>>()
    val listArticle = MutableLiveData<List<Article>>()
    val listCategory = MutableLiveData<MutableList<CategoryFood>>()
    val listFoodById = MutableLiveData<MutableList<Food>>()
    val listFoodSuggest = MutableLiveData<MutableList<Food>>()
    val mapList = MutableLiveData<MutableList<ModelMap>>()
    val location = MutableLiveData<Response<ModelMap>>()
    val backUpFile = MutableLiveData<Response<BaseResponse<String>>>()
    val deleteHistory = MutableLiveData<Response<BaseResponse<String>>>()
    val userBackUpFile = MutableLiveData<Response<BaseResponse<String>>>()
    val listSearchFood = MutableLiveData<Response<ResponseFood>>()

    fun getUserData(email: String) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.getProfile(email)
                }
            }
                .onSuccess {
                    dataExercise.value = repositoryApi.getProfile(email)
                }
                .onFailure {

                }
        }
    }

    fun updateProfile(
        email: String,
        gender: String,
        age: String,
        tall: String,
        weight: String,
        firstname: String,
        lastname: String,
        progess: Int
    ) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.updateProfile(
                        email,
                        gender,
                        age,
                        tall,
                        weight,
                        firstname,
                        lastname,
                        progess
                    )
                }
            }
                .onSuccess {
                    updateUser.value = repositoryApi.updateProfile(
                        email,
                        gender,
                        age,
                        tall,
                        weight,
                        firstname,
                        lastname,
                        progess
                    )
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }

    fun updateUserProgess(
        email: String,
        progess: Int
    ) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.updateUserProgess(
                        email,
                        progess
                    )
                }
            }
                .onSuccess {
                    updateUser.value = repositoryApi.updateUserProgess(
                        email,
                        progess
                    )
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.login(email, password)
                }
            }
                .onSuccess {
                    login.value = it
                }
                .onFailure {

                }
        }
    }

    fun register(email: String, password: String, firstname: String, lastname: String) {
        viewModelScope.launch {
            register.value = repositoryApi.register(email, password, firstname, lastname)
        }
    }

    fun getHistoryById(idUser: String) {
        viewModelScope.launch {
            downloadHistory.value = repositoryApi.getHistoryById(idUser)
        }
    }

    fun uploadHistoryUser(
        id_user: String,
        date: String,
        time: String,
        activity: String,
        type: String,
        value: String
    ) {
        viewModelScope.launch {
            uploadHistory.value =
                repositoryApi.uploadHistoryUser(id_user, date, time, activity, type, value)
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

    fun getCategoryFood() {
        viewModelScope.launch {
            listCategory.value = repositoryApi.getCategoryFood().body()?.data
        }
    }

    fun getSuggestFood(progess: Int) {
        viewModelScope.launch {
            listFoodSuggest.value = repositoryApi.getSuggestFood(progess).body()?.data
        }
    }

    fun getFoodById(id: String) {
        viewModelScope.launch {
            listFoodById.value = repositoryApi.getFoodById(id).body()?.data
        }
    }

    fun getFoodByIdCategory(id: Int) {
        viewModelScope.launch {
            if (repositoryApi.getFoodByIdCategory(id).body()?.response == 1) {
                listFoodById.value = repositoryApi.getFoodByIdCategory(id).body()?.data
            } else listFoodById.value = mutableListOf()
        }
    }

    fun getDataMap(lat: Double, lng: Double, raduis: String) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.getDataMap(lat, lng, raduis)
                }
            }
                .onSuccess {
                    if (!it.body()?.data.isNullOrEmpty()) mapList.value =
                        it.body()?.data?.toMutableList()
                    else mapList.value = mutableListOf()
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }

    fun getLocation(id: Int) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.getLocation(id)
                }
            }
                .onSuccess {
                    location.value = it
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }

    fun getBackUpFile(email: String) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.getBackUpFile(email)
                }
            }
                .onSuccess {
                    backUpFile.value = it
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }

    fun deleteHistory(idUser: Int) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.deleteHistory(idUser)
                }
            }
                .onSuccess {
                    deleteHistory.value = it
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }

    fun updateUserBackUp(email: String, backuptime: String) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.updateUserBackUp(email, backuptime)
                }
            }
                .onSuccess {
                    userBackUpFile.value = it
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }

    fun searchFood(name: String) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repositoryApi.searchFood(name)
                }
            }
                .onSuccess {
                    listSearchFood.value = it
                }
                .onFailure {
                    error.value = it.toString()
                }
        }
    }
}