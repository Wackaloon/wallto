/*
 * Created by Mark Abramenko on 10/30/19 8:07 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 10/30/19 8:07 PM
 */

package com.example.wallto.ui.start.register.domain

sealed class RegistrationResult {
    object Success : RegistrationResult()
    data class Failure(val error: String) : RegistrationResult()
}
