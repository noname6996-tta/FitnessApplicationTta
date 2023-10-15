package com.tta.fitnessapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "meal_planner")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var time: String,
    var date: String,
    var kcal: String,
    var protein: String,
    var carb: String,
    var fat: String,
    var image: Int? = null,
    var type: Int
) : Serializable