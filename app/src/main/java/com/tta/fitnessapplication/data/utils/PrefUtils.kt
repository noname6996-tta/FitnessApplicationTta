package com.tta.fitnessapplication.data.utils

import android.content.Context
import android.content.SharedPreferences

class PrefUtils(val context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(Constant.PREF.PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()


    fun setLoggedIn(isLoggedIn: Boolean) {
        Constant.PREF.PREF_LOGGED_IN.put(isLoggedIn)
    }

    fun getLoggedIn() = Constant.PREF.PREF_LOGGED_IN.getBoolean()

    fun setToken(msg: String) {
        Constant.PREF.PREF_TOKEN.put(msg)
    }

    fun getToken() = "Bearer " + Constant.PREF.PREF_TOKEN.getString()


    fun clearData() {
        editor.clear()
        editor.commit()
    }


    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)


}