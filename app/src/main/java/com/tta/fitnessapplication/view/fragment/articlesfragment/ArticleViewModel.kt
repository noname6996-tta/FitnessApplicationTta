package com.tta.fitnessapplication.view.fragment.articlesfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.fitnessapplication.api.ApiClient
import com.tta.fitnessapplication.data.model.Article
import com.tta.fitnessapplication.data.model.ResoinseArticle
import com.tta.fitnessapplication.data.model.ResponseVideo
import com.tta.fitnessapplication.data.model.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {
    val listVideo = MutableLiveData<List<Video>>()
    val listArticle = MutableLiveData<List<Article>>()
    val message = MutableLiveData<String>()
    fun getListVideo() {
        ApiClient.API.getVideo().enqueue(object : Callback<ResponseVideo> {
            override fun onResponse(call: Call<ResponseVideo>, response: Response<ResponseVideo>) {
                if (response.body()?.response == 1) {
                    listVideo.value = response.body()?.data
                } else {
                    message.postValue(response.body()?.message)
                }
            }

            override fun onFailure(call: Call<ResponseVideo>, t: Throwable) {
                message.postValue(t.toString())
            }

        })
    }

    fun getListArticle() {
        ApiClient.API.getArticle().enqueue(object : Callback<ResoinseArticle> {
            override fun onResponse(call: Call<ResoinseArticle>, response: Response<ResoinseArticle>) {
                if (response.body()?.response == 1) {
                    listArticle.value = response.body()?.data
                } else {
                    message.postValue(response.body()?.message)
                }
            }

            override fun onFailure(call: Call<ResoinseArticle>, t: Throwable) {
                message.postValue(t.toString())
            }

        })
    }
}