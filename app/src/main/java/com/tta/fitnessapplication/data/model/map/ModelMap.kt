package com.tta.fitnessapplication.data.model.map

import com.google.gson.annotations.SerializedName

data class ModelMap(
    @SerializedName("ID")
    var id: Int,
    @SerializedName("Name")
    var name: String,
    @SerializedName("Lat")
    var lat: Double,
    @SerializedName("Lng")
    var lng: Double
)