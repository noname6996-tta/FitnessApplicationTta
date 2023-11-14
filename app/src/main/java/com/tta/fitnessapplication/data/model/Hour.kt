package com.tta.fitnessapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hour_table")
data class Hour(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var date: String,
    var time: String,
    var idBed: Int,
    var idWake: Int
)
