package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("success")
    var success: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("id")
    var id: String
)
