package com.tta.fitnessapplication.data.model

import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("id_user")
    var id_user : Int? = null,
    @SerializedName("datetime")
    var datetime : String? = null,
    @SerializedName("activity")
    var activity : String? = null,
    @SerializedName("type")
    var type : Int? = null,
    @SerializedName("value")
    var value : String? = null,
)
