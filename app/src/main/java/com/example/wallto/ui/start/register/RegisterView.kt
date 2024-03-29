/*
 * Created by Mark Abramenko on 12.08.19 20:11
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 12.08.19 20:11
 */

package com.example.wallto.ui.start.register

import com.example.wallto.base.BaseView

interface RegisterView : BaseView {
    fun showError(string: String?)
    // не стоит в нейминге интерфейса указывать детали реализации, а если надо будет показать
    // не тост, а диалог? нужно будет переименовать метод, что не есть хорошо
    fun showToast(string: String?)
    fun openNextActivity()
}
