package com.tta.fitnessapplication.data.model

data class Exercise(
    var id: Int,
    var name: String,
    var image: String,
    var title: String,
    var area: String,
    var type: String,
    var number: String
)

/*
Nếu type = 0 th number tính bằng giây
ngược lại number là số lần thực hiện
 */


