package com.example.kotlinauthorizationwithcamera

import android.content.Context

object DataSettings {
    var isLoaded: Boolean = false
    var isHaveData: Boolean = false
        private set

    var password: String? = null
        private set

    val PREF_NAME = "DataBase_TestApplication"
    val KEY_NAME = "Password_TestApplication"

    fun SaveData(password: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, password)
        editor.apply()
    }

    fun RemoveData(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, null)
        editor.apply()
    }

    fun LoadData(context: Context) {
        if (!isLoaded) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            password = sharedPreferences.getString(KEY_NAME, "")

            if (password == null || password!!.length <= 4) {
                password = null
            }

            isHaveData = password != null

            isLoaded = true
        }
    }
}