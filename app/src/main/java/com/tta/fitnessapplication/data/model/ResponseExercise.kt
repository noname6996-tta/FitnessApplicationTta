package com.tta.fitnessapplication.data.model

data class ResponseExercise(
    var response : Int,
    var message : String,
    var data : List<Exercise>
)
