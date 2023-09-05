package com.tta.fitnessapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_planner")
data class Water(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var date: String,
    var time: String,
    var activity: String,
    var type: String,
    var value: String
)
