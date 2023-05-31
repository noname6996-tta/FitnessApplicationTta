package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class Tool(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("image")
    var image: String
)
