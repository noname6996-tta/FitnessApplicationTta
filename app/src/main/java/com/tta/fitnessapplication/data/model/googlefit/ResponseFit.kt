package com.tta.fitnessapplication.data.model.googlefit

import com.google.gson.annotations.SerializedName

data class ResponseFit(
    @SerializedName("steps_count")
    var steps_count: List<DataFit>,
    @SerializedName("calories_expended")
    var calories_expended: List<DataFit>,
    @SerializedName("heart_minutes")
    var heart_minutes: List<DataFit>,
    @SerializedName("sleep_segment")
    var sleep_segment: List<DataFit>,
    @SerializedName("weight")
    var weight: List<DataFit>,
)
