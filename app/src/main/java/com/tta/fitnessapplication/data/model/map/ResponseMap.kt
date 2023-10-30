package com.tta.fitnessapplication.data.model.map

import com.google.gson.annotations.SerializedName

data class ResponseMap(
    @SerializedName("response")
    var response: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("data")
    var data: List<ModelMap>
)
