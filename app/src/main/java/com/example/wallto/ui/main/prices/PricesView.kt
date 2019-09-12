/*
 * Created by Mark Abramenko on 12.09.19 12:13
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 12.09.19 12:13
 */

package com.example.wallto.ui.main.prices

import com.example.wallto.base.BaseView
import com.example.wallto.data.Currency

interface PricesView : BaseView {
    fun displayData(prices: ArrayList<Currency>)
    fun hideSwipe()
    fun hideProgressBar()
    val isFragmentExist: Boolean
}