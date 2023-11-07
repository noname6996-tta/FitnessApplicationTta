package com.tta.fitnessapplication.view.activity.tracker.calortracker.calculate

fun calculateBMI(weight: Double, height: Double): Double {
    val heightInMeter = height / 100 // chuyển chiều cao từ cm sang mét
    return weight / (heightInMeter * heightInMeter)
}

fun calculateBMIAndSetText(weight: Double, height: Double): String {
    val heightInMeter = height / 100 // chuyển chiều cao từ cm sang mét
    val bmi = weight / (heightInMeter * heightInMeter)
    when {
        bmi < 18.5 -> return "Underweight"
        bmi > 18.5 && bmi < 24.9 -> return "Normal weight"
        bmi > 25.0 && bmi < 29.9 -> return "Overweight"
        bmi > 30.0 && bmi < 34.9 -> return "Obese class 1"
        bmi > 35.0 && bmi < 39.9 -> return "Obese class 2"
        else -> return "Obese class 3"
    }
}

// Hàm tính lượng protein
fun calculateProtein(calories: Double): Double {
    return calories * 0.15 / 4 // 1 gram protein cung cấp 4 calo
}

// Hàm tính lượng carbohydrate
fun calculateCarbohydrate(calories: Double): Double {
    return calories * 0.55 / 4 // 1 gram carbohydrate cung cấp 4 calo
}

// Hàm tính lượng chất béo
fun calculateFat(calories: Double): Double {
    return calories * 0.3 / 9 // 1 gram chất béo cung cấp 9 calo
}

// Ví dụ sử dụng
fun main() {
    val totalCalories = 2000.0 // Tổng lượng calo hàng ngày
    val protein = calculateProtein(totalCalories)
    val carbohydrate = calculateCarbohydrate(totalCalories)
    val fat = calculateFat(totalCalories)

    println("Lượng protein cần ăn: $protein gram")
    println("Lượng carbohydrate cần ăn: $carbohydrate gram")
    println("Lượng chất béo cần ăn: $fat gram")
}

// Hàm tính lượng calo hàng ngày
fun calculateDailyCalories(
    weight: Double, // Cân nặng (kg)
    height: Double, // Chiều cao (cm)
    age: Int, // Tuổi
    isMale: Boolean, // Giới tính: true nếu là nam, false nếu là nữ
    activityLevel: Double // Mức độ hoạt động (từ 1.2 đến 2.5)
): Double {
    val bmr: Double
    if (isMale) {
        bmr = 10 * weight + 6.25 * height - 5 * age + 5
    } else {
        bmr = 10 * weight + 6.25 * height - 5 * age - 161
    }
    val dailyCalories = bmr * activityLevel
    return dailyCalories
}

// Ví dụ sử dụng
//fun main() {
//    val weight = 70.0 // Cân nặng (kg)
//    val height = 170.0 // Chiều cao (cm)
//    val age = 30 // Tuổi
//    val isMale = true // Giới tính: true nếu là nam, false nếu là nữ
//    val activityLevel = 1.5 // Mức độ hoạt động (từ 1.2 đến 2.5)
//
//    val dailyCalories = calculateDailyCalories(weight, height, age, isMale, activityLevel)
//
//    println("Lượng calo hàng ngày cần thiết: $dailyCalories calo")
//}


