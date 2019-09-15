/*
 * Created by Mark Abramenko on 12.09.19 12:18
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 05.09.19 14:05
 */

package com.example.wallto.ui.main.prices

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.wallto.R
import com.example.wallto.data.Currency
import com.example.wallto.network.PriceApi
import com.example.wallto.network.services.InfoService
import com.example.wallto.ui.MainActivity
import com.example.wallto.ui.main.pricelist.PriceAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PricesFragment : Fragment(), PricesView {
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var progress: ProgressBar
    private lateinit var infoService: InfoService
    private lateinit var recyclerView: android.support.v7.widget.RecyclerView
    private var TAG = this.javaClass.simpleName
    private val presenter = PricesPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_prices, container, false)

        presenter.initNetwork()

        recyclerView = v.findViewById(R.id.recyclerPrices)
        recyclerView.layoutManager = LinearLayoutManager(context)

        progress = v.findViewById(R.id.progressPrices)
        progress.visibility = ProgressBar.VISIBLE

        swipe = v.findViewById(R.id.swipePrices)
        swipe.setOnRefreshListener {
            presenter.onRefreshAction()
        }

        presenter.getData()

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val act = activity as MainActivity
        act.supportActionBar!!.show()
        act.supportActionBar!!.title = "Курсы"
    }

    override fun displayData(prices: ArrayList<Currency>) {
        val adapter = PriceAdapter(prices, context!!)
        recyclerView.adapter = adapter
    }

    override fun writeLog(mes: String?) {
        Log.e(TAG, mes)
    }

    override fun writeLog(mes: String?, e: Throwable?) {
        Log.e(TAG, mes, e)
    }

    override fun hideSwipe() {
        swipe.isRefreshing = false
    }

    override fun hideProgressBar() {
        progress.visibility = ProgressBar.INVISIBLE
    }

    override val isFragmentExist: Boolean get() = context != null
}