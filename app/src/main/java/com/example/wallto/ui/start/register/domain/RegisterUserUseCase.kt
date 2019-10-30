/*
 * Created by Mark Abramenko on 10/30/19 8:09 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 10/30/19 8:08 PM
 */

package com.example.wallto.ui.start.register.domain

import io.reactivex.Single

/**
 * Регистрирует пользователя и возвращает результат в виде [RegistrationResult].
 */
interface RegisterUserUseCase {
    fun registerUser(login: String, password: String): Single<RegistrationResult>
}
