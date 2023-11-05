package com.tta.fitnessapplication.data.model.map

import com.google.gson.annotations.SerializedName

data class ModelMap(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("info")
    var info: String,
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lng")
    var lng: Double,
    @SerializedName("type")
    var type: Int
)