package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("success")
    var success: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("error")
    var email: String,
)
