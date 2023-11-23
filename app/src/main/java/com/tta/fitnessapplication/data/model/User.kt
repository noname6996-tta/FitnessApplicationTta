package com.tta.fitnessapplication.data.model

data class User(
    var id: Int,
    var image: String,
    var gender: String,
    var age: Int,
    var tall: String,
    var weight: String,
    var height: String,
    var phone: String,
    var email: String,
    var password: String,
    var firstname: String,
    var lastname: String,
    var progess: Int? = null,
    var backuptime : String? = null
)
