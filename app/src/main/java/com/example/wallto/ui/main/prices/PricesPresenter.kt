/*
 * Created by Mark Abramenko on 12.09.19 12:13
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 12.09.19 12:13
 */

package com.example.wallto.ui.main.prices

import android.annotation.SuppressLint
import com.example.wallto.base.BasePresenter
import com.example.wallto.data.Currency
import com.example.wallto.network.PriceApi
import com.example.wallto.network.services.InfoService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class PricesPresenter (val view: PricesView) : BasePresenter {
    private lateinit var infoService: InfoService

    override fun initNetwork() {
        val retrofit = PriceApi.getInstance()
        infoService = retrofit.create(InfoService::class.java)
    }

    fun onRefreshAction() {
        getData()
    }

    @SuppressLint("CheckResult")
    fun getData() {
        infoService.prices
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val prices = writeData(it)
                    view.hideProgressBar()
                    view.hideSwipe()
                    view.displayData(prices)
                    view.writeLog("Success")
                },
                {
                    view.writeLog("Loading error", it)
                })
    }

    private fun writeData(list: ArrayList<Currency>): ArrayList<Currency> {
        val prices = ArrayList<Currency>()
        if (view.isFragmentExist) {
            for (i in 0..7) {
                prices.add(list[i])
            }
        }
        return prices
    }
}