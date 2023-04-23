package com.tta.fitnessapplication.data.model

data class ResponseProfile(
    var success: Int,
    var message: String,
    var data: List<User>
)
