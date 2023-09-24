package com.tta.fitnessapplication.data.model

data class ResponseCategoryFood(
    var response : Int,
    var message : String,
    var data : MutableList<CategoryFood>
)
