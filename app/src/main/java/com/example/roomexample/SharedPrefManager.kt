package com.example.roomexample

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPrefManager {
    companion object {
        var preferences : SharedPreferences? = null

        fun setup(ctx: Context) {
            preferences = PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun getString(idStr: String): String? {
            if (preferences == null) return null
            return preferences!!.getString(idStr, "")
        }

        fun getStringSet(idStr: String): Set<String>? {
            if (preferences == null) return null
            return preferences!!.getStringSet(idStr, null)
        }


        fun putString(nameStr: String, strToSave: String): Boolean {
            if(preferences == null) return false

            var editor = preferences!!.edit()

            if(nameStr.isEmpty() || strToSave.isEmpty()) return false

            editor.putString(nameStr, strToSave)

            editor.apply()
            return true
        }

        fun putStringSet(nameStr: String, vars: Set<String>) : Boolean {
            if(preferences == null) return false

            var editor = preferences!!.edit()

            if(nameStr.isEmpty() || vars.isNullOrEmpty()) return false

            editor.putStringSet(nameStr, vars)

            editor.apply()
            return true
        }
    }
}