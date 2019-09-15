/*
 * Created by Mark Abramenko on 15.09.19 21:22
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 15.09.19 21:22
 */

package com.example.wallto.ui.main.home

import com.example.wallto.base.BaseView

interface HomeView : BaseView {
    fun showLogin(name: String)
    fun showDecrementAnimation(baseHeight: Int)
    fun setArcValue(baseHeight: Int)
    fun showIncrementAnimation(baseHeight: Int)
}