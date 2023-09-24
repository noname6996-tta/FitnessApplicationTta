package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("id")
    var id: Int,
    @SerializedName("id_category")
    var id_category: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("author")
    var author: String,
    @SerializedName("nutrition")
    var nutrition: String,
    @SerializedName("descriptions")
    var descriptions: String,
    @SerializedName("ingredients")
    var ingredients: String,
    @SerializedName("steps")
    var steps: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("video")
    var video: String,
    @SerializedName("type")
    var type: String
)