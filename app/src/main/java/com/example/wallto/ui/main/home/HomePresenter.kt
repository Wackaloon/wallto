/*
 * Created by Mark Abramenko on 15.09.19 21:21
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 15.09.19 21:21
 */

package com.example.wallto.ui.main.home

import com.example.wallto.base.BasePresenter
import com.example.wallto.network.PriceApi
import com.example.wallto.network.services.InfoService
import com.example.wallto.utils.PrefsRepository
import com.example.wallto.utils.PrefsRepository.Keys

class HomePresenter(val view: HomeView) : BasePresenter {
    private lateinit var infoService: InfoService
    private lateinit var username: String
    private var currentScrollY = 0
    private var prevScrollY = 0
    private var baseHeight = 80

    override fun initNetwork() {
        val retrofit = PriceApi.getInstance()
        infoService = retrofit.create(InfoService::class.java)
    }

    fun initUserData() {
        username = PrefsRepository.getValue(Keys.LOGIN.toString()).toString()
        view.showLogin(username)
    }

    fun notifyScrollChanged(y: Int) {
        currentScrollY = y
        startAnimation()
    }

    private fun startAnimation() {
        if (prevScrollY <= currentScrollY) doHeightDecrement()
        else doHeightIncrement()
    }

    private fun doHeightIncrement() {
        prevScrollY = currentScrollY
        if (currentScrollY < 400) {
            if (baseHeight < 80) {
                if (currentScrollY <= 0) {
                    view.showIncrementAnimation(baseHeight)
                    baseHeight = 80
                } else {
                    baseHeight += 1
                    view.setArcValue(baseHeight)
                }
            }
        }
    }

    private fun doHeightDecrement() {
        prevScrollY = currentScrollY
        if (baseHeight > 0) {
            if (currentScrollY > 400) {
                view.showDecrementAnimation(baseHeight)
                baseHeight = 0
            } else if (currentScrollY > 0) {
                baseHeight -= 1
                view.setArcValue(baseHeight)
            }
        }
    }
}