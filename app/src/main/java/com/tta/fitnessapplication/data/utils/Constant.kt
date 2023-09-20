package com.tta.fitnessapplication.data.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Constant {
    companion object {
        //        const val BASE_URL = "http://192.168.1.6/"
        const val BASE_URL = "https://tta682001.000webhostapp.com/"
        const val BASE_URL_DEMO = "https://v1.nocodeapi.com/trantheanh/fit/"
        const val SAVE_USER = "saveLogin"
        const val EMAIL_USER = "EMAIL_USER"
        const val LOGIN_PREFS = "loginPrefs"
        const val SAVE_LOGIN = "saveLogin"
        const val MAX_TIMESTAMP = 8640000000000000
        const val sDate = 1640908800 // no of seconds passed from 1970 to 2021
    }

    object PREF {
        const val PREFERENCE_NAME = "InfoApp"
        const val PREF_LOGGED_IN = "pref_login"
        const val PREF_TOKEN = "pref_token"
        const val IDUSER = "IDUSER"
        const val WATER_INNEED = "WATER_INNEED"
    }

    object DATE {
        val today: LocalDate = LocalDate.now()
        val titleSameYearFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM")
        val titleFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
        val selectionFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
        val fullDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    object GPT {
        //    val API_KEY = "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"
        val API_KEY = "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"
    }
}