/*
 * Created by Mark Abramenko on 12.08.19 20:12
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 12.08.19 20:12
 */

package com.example.wallto.ui.start.register

import android.annotation.SuppressLint
import com.example.wallto.base.BasePresenter
import com.example.wallto.data.body.UserBody
import com.example.wallto.network.RestApi
import com.example.wallto.network.services.AuthService
import com.example.wallto.ui.start.register.data.RegistrationRepository
import com.example.wallto.ui.start.register.domain.RegisterUserUseCase
import com.example.wallto.ui.start.register.domain.RegistrationResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class RegisterPresenter(private val view: RegisterView) : BasePresenter {

    private lateinit var authService: AuthService
    private lateinit var login: String
    private lateinit var password: String
    private lateinit var passwordAgain: String

    // этот метод можно было объединить с sendData и doUserRegister, а так получается больше путаницы
    // смотри новый метод
    fun checkDataValid() {
        if (isLoginValid && isPasswordValid) {
            if (isPasswordsCons) {
                doUserRegister()
            } else view.showError("Пароли не совпадают")
        } else view.showError("Ошибка ввода")
    }

    override fun initNetwork() {
        val retrofit = RestApi.getInstance()
        authService = retrofit.create(AuthService::class.java)
    }

    fun sendData(login: String, password: String, passwordAgain: String) {
        this.login = login
        this.password = password
        this.passwordAgain = passwordAgain
    }

    @SuppressLint("CheckResult")
    private fun doUserRegister() {
        // в целом идея дергать сервис прям из презентера не очень, потом это сложно поддерживать
        // и еще сложнее менять, можно сделать UseCase который будет регистрировать пользователя
        // этот бз кейс будет внутри использовать репозиторий , который с помощью сервиса уже будет
        // лезть в сеть и производить маппинг результата, тогда в презентере останется лишь обработать
        // заранее оговоренный контракт с юз кейсом, если на бэкенде что-то поменется -
        // изменения коснутся только репозитория
        val userBody = UserBody(login, password)
        authService.signUp("gnomes", userBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { user ->
                    checkRegisterSuccess(user.message)
                },
                { throwable ->
                    view.showError("Ошибка регистрации: " + throwable.message)
                    view.writeLog("Error doUserRegister", throwable)
                }
            )
    }

    private fun checkRegisterSuccess(message: String?) {
        if (message == "Successfully") {
            view.showToast("Авторизация прошла успешно")
            successRegister()
        } else {
            view.showError(message)
            view.writeLog(message)
        }
    }

    private fun successRegister() {
        view.openNextActivity()
    }

    private val isLoginValid: Boolean get() = login.isNotEmpty()
    private val isPasswordValid: Boolean get() = password.isNotEmpty()
    private val isPasswordsCons: Boolean get() = Objects.equals(password, passwordAgain)

    /**
     * Пример реализации этого же презентера в демонстрационных целях.
     */

    private val registerUserUseCase: RegisterUserUseCase = RegistrationRepository()
    // хранит подписки на реактивные запросы, по хорошему должен быть очищен при остановке активити
    private val disposable = CompositeDisposable()

    private fun Disposable.trackDisposable() {
        disposable.add(this)
    }

    @SuppressLint("CheckResult")
    private fun doUserRegisterBeautiful(
        login: String,
        password: String,
        passwordAgain: String
    ) {
        if (checkDataIsValid(login, password, passwordAgain)) {
            authorizeUser(login, password)
        }
    }

    private fun checkDataIsValid(
        login: String,
        password: String,
        passwordAgain: String
    ): Boolean {
        // мелкие проверки лучше не выносить в отдельный val или метод, это лучше для читаемоести
        if (login.isEmpty() || password.isEmpty()) {
            view.showError("Ошибка ввода")
            return false
        }
        if (password != passwordAgain) {
            view.showError("Пароли не совпадают")
            return false
        }
        return true
    }

    private fun authorizeUser(
        login: String,
        password: String
    ) {
        registerUserUseCase.registerUser(login, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    when (result) {
                        is RegistrationResult.Success -> handleSuccefullRegistration()
                        is RegistrationResult.Failure -> displayRegistrationFailed(result.error)
                    }
                },
                //хорошая практика всегда обрабатывать любые ошибки
                { throwable ->
                    view.showError("Ошибка регистрации: " + throwable.message)
                    view.writeLog("Error doUserRegister", throwable)
                }
            )
            .trackDisposable()
    }

    private fun handleSuccefullRegistration() {
        view.showToast("Авторизация прошла успешно")
        navigateToTheNextScreen()
    }

    private fun navigateToTheNextScreen() {
        // для навигации лучше всего создать класс навигатора и передавать ему активити в конструкторе
        // таким образом можно убрать у view лишнюю ответственность за навигацию
        view.openNextActivity()
    }

    private fun isRegistrationSuccessfull(response: String): Boolean {
        return response == "Successfully"
    }

    private fun displayRegistrationFailed(errorMessage: String) {
        view.showError(errorMessage)
        view.writeLog(errorMessage)
    }
}
