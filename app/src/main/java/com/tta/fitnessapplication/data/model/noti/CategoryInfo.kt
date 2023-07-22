package com.tta.fitnessapplication.data.model.noti

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "categoryInfo")
data class CategoryInfo(
    @PrimaryKey
    var categoryInformation: String,
    var color: String
) : Serializable
