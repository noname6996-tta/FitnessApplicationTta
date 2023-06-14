package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("response")
    val response: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: T? = null
)
