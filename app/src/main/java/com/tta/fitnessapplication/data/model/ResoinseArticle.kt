package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class ResoinseArticle(
    @SerializedName("response")
    var response: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("data")
    var data: List<Article>
)
