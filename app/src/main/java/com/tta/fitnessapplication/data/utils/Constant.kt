package com.tta.fitnessapplication.data.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Constant {
    companion object {
        //        const val BASE_URL = "http://192.168.1.6/"
        const val BASE_URL = "https://tta682001.000webhostapp.com/"
        const val BASE_URL_XMAPP = "http://192.168.1.2/"
        const val BASE_URL_MAP = "https://maps.googleapis.com/"
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
        const val CALO_INNEED = "CALO_INNEED"
        const val SLEEP_TIME = "SLEEP_TIME"
        const val WAKEUP_TIME = "WAKEUP_TIME"
        const val PROCESS_USER = "PROCESS_USER"
        const val AUTO_BACKUP = "AUTO_BACKUP"
    }

    object DATE {
        val today: LocalDate = LocalDate.now()
        fun getYesterdayDate(): LocalDate {
            return LocalDate.now().minusDays(1)
        }
        val titleSameYearFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM")
        val titleFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
        val selectionFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
        val fullDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    object GPT {

    }
}