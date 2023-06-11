package com.tta.fitnessapplication.view.activity.watertracker.watercaculate

enum class Gender {
    MALE,
    FEMALE
}

enum class ActivityLevel {
//    ít vận động,
//    VỪA PHẢI,
//    TÍCH CỰC
    SEDENTARY,
    MODERATE,
    ACTIVE
}

enum class Climate {
    COLD,
    MILD,
    HOT,
    VERY_HOT
}

data class HealthInformation(val age: Int, val gender: Gender, val weight: Double, val climate: Climate, val activityLevel: ActivityLevel)

fun calculateWaterIntake(healthInformation: HealthInformation): Double {
    val baseWaterIntake = when (healthInformation.gender) {
        Gender.MALE -> 3.7 //For Men
        Gender.FEMALE -> 2.7 //For Women
    }
    var waterIntake = baseWaterIntake * healthInformation.weight // Weight calculation
    when (healthInformation.activityLevel) { // Activity level calculation
        ActivityLevel.SEDENTARY -> {
            waterIntake *= 1 // No additional adjustment
        }
        ActivityLevel.MODERATE -> {
            waterIntake *= 1.2 // Moderate adjustment
        }
        ActivityLevel.ACTIVE -> {
            waterIntake *= 1.5 // Active adjustment
        }
    }
    when (healthInformation.climate) { // Climate adjustment calculation
        Climate.COLD -> {
            waterIntake += 0.2 // Increases by 20%
        }
        Climate.MILD -> {
            waterIntake += 0.1 // Increases by 10%
        }
        Climate.HOT -> {
            waterIntake += 0.4 // Increases by 40%
        }
        Climate.VERY_HOT -> {
            waterIntake += 0.6 // Increases by 60%
        }
    }
    if (healthInformation.age <= 30) { // Age adjustment calculation
        waterIntake += 0.4 // Increases by 40%
    } else if (healthInformation.age > 30 && healthInformation.age <= 55) {
        waterIntake += 0.2 // Increases by 20%
    } else {
        waterIntake += 0 // no additional adjustment
    }
    return waterIntake
}

//Sample usage
//val healthInformation = HealthInformation(30, Gender.MALE, 75.0, Climate.MILD, ActivityLevel.MODERATE)
//val recommendedWaterIntake = calculateWaterIntake(healthInformation)
//println("Recommended daily water intake: $recommendedWaterIntake liters")
