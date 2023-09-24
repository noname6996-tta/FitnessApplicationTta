package com.tta.fitnessapplication.data.model

data class ResponseFood(
    var response : Int,
    var message : String,
    var data : MutableList<Food>
)