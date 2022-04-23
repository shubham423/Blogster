package com.example.blogster.utils

import android.content.Context
import android.content.SharedPreferences


object PrefsHelper {

    private lateinit var prefs: SharedPreferences

    private const val PREFS_NAME = "BLOGSTER"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String, value: String?): String? {
        return prefs.getString(key, value)
    }

    fun remove(key: String){
        prefs.edit().remove(key).apply()
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }
}