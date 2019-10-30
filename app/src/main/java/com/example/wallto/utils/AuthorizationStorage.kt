/*
 * Created by Mark Abramenko on 10/30/19 7:36 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 10/30/19 7:36 PM
 */

package com.example.wallto.utils

/**
 * Хранилище данных для авторизации.
 *
 * Обязательно пиши документацию ко всем интерфейсам, это поможет потом вспомнить зачем они были
 * сделаны и как ими пользоваться.
 */
interface AuthorizationStorage {
    fun saveToken(token: String)
    fun getToken(): String

    fun saveLogin(login: String)
    fun getLogin(): String

    var pin: String
}
