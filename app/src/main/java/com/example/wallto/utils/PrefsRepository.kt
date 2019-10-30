package com.example.wallto.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

// в целом синглтоны это зло, в данном случае я бы рекомендовал сделать несколько классов
// каждый из которых будет иметь конкретные методы для того чтобы сохранять/извлекать данные
// спрятать их за интерфейсами - и тогда имплемантация в виде шарен преференс будет уже мелкой
// деталью, которую легко заменить за базу данных или вообще сохранение в файл
class PrefsRepository {

    enum class Keys {
        TOKEN,
        LOGIN,
        EMAIL,
        PIN
    }

    companion object {
        private lateinit var prefs: SharedPreferences
         // зачем эдитор вынесен отдельно? можно просто каждый раз создавать переменную
        private lateinit var editor: SharedPreferences.Editor

        fun init(context: Context) {
            prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
        }

        fun putValue(key: String, value: String) {
            editor = prefs.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getValue(key: String): String? {
            return prefs.getString(key, "")
        }

        fun removeValue(key: String) {
            editor = prefs.edit()
            editor.remove(key)
            editor.apply()
        }
    }
}
