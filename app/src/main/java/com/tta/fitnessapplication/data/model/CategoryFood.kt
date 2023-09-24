package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class CategoryFood(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("icon")
    var icon: String
)
