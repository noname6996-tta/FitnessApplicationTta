package com.tta.fitnessapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "meal_planner")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var kcal: String,
    val desc: String,
    var type: Int,
    var image: String? = null,
    var enable : Boolean
) : Serializable