package com.tta.fitnessapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("id_user")
    var id_user : Int? = null,
    @SerializedName("date")
    var date : String? = null,
    @SerializedName("time")
    var time : String? = null,
    @SerializedName("activity")
    var activity : String? = null,
    @SerializedName("type")
    var type : Int? = null,
    @SerializedName("value")
    var value : String? = null,
)
