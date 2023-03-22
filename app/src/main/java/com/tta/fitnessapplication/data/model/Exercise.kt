package com.tta.fitnessapplication.data.model

data class Exercise(
    var id: Int,
    var name: String,
    var image: String,
    var description: String,
    var type: Int,
    var number: Int,
    var link: String
)
