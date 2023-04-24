package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("response")
    var response : Int,
    @SerializedName("message")
    var message : String
)
