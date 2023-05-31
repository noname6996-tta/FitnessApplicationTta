package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("time")
    var time: String,
    @SerializedName("url")
    var url : String
)
