package com.tta.fitnessapplication.data.model.googlefit

import com.google.gson.annotations.SerializedName

data class DataFit(
    @SerializedName("value")
    var value: String,
    @SerializedName("startTimeMillis")
    var startTimeMillis: String,
    @SerializedName("endTimeMillis")
    var endTimeMillis: String,
    @SerializedName("startTime")
    var startTime: String,
    @SerializedName("endTime")
    var endTime: String
)