package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("link")
    var link: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("tittle")
    var tittle : String
)