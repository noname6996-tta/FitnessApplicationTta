package com.tta.fitnessapplication.data.model

data class Meal(
    var id: Int,
    var name: String,
    var time: String,
    var date: String,
    var kcal: String,
    var protein: String,
    var carb: String,
    var fat: String,
    var image: Int? = null
)