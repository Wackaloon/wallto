package com.example.wallto.utils

import android.content.SharedPreferences

private const val TOKEN_KEY = "TOKEN"
private const val LOGIN_KEY = "LOGIN"
private const val PIN_KEY = "PIN"

/**
 *  Пример реализации хранилища данных.
 *
 *  В таком вариенте его легко мокнуть в тестах или вообще заменить на другую реализацию,
 *  например с базой данных.
 */
class AuthorizationStorageImpl(private val prefs: SharedPreferences) : AuthorizationStorage {
    override fun saveToken(token: String) {
        prefs.put(TOKEN_KEY, token)
    }

    // в большинстве случаев не стоит использовать !!, здесь точно не может быть null, так что норм
    override fun getToken(): String = prefs.getString(TOKEN_KEY, "")!!

    override fun saveLogin(login: String) {
        prefs.put(LOGIN_KEY, login)
    }

    override fun getLogin(): String = prefs.getString(TOKEN_KEY, "")!!

    // пример как можно реализовать хранение простых значений
    override var pin: String
        get() = prefs.getString(PIN_KEY, "")!!
        set(value) {
            prefs.put(PIN_KEY, value)
        }

    private fun SharedPreferences.put(key: String, value: String) {
        this.edit().apply {
            putString(key, value)
            apply()
        }
    }
}
