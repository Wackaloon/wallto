/*
 * Created by Mark Abramenko on 10/30/19 8:09 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 10/30/19 8:09 PM
 */

package com.example.wallto.ui.start.register.data

import com.example.wallto.data.body.UserBody
import com.example.wallto.network.RestApi
import com.example.wallto.network.services.AuthService
import com.example.wallto.ui.start.register.domain.RegisterUserUseCase
import com.example.wallto.ui.start.register.domain.RegistrationResult
import io.reactivex.Single

// в данном случае репозиторий сразу имплементирует юх кейс т.к. юз кейс был бы просто проксирующим
// классом, лишь раздувающим архитектуру, если бы была какая-то бизнесс логика - то был бы отдельный
// класс с реализацией юз кейса использующий интерфейс репозитория, который был бы объявлен в домен пакете
class RegistrationRepository : RegisterUserUseCase {
    private val authService = RestApi.getInstance().create(AuthService::class.java)

    override fun registerUser(login: String, password: String): Single<RegistrationResult> {
        // теперь за создание всего нужнго для запроса отвечает репозиторий
        val userBody = UserBody(login, password)
        return authService.signUp("gnomes", userBody)
            .map {
                if (it.message == "Successfully") {
                     return@map RegistrationResult.Success
                } else {
                    return@map RegistrationResult.Failure(it.message?:"")
                }
            }
    }

}
