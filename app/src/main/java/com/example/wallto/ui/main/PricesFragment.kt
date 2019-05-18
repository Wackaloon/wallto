package com.example.wallto.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import carbon.widget.ExpandableRecyclerView
import carbon.widget.RecyclerView
import com.example.wallto.R
import com.example.wallto.model.Currency
import com.example.wallto.model.PriceResponse
import com.example.wallto.network.RestApi
import com.example.wallto.network.services.AuthService
import com.example.wallto.network.services.InfoService
import com.example.wallto.ui.main.pricelist.PriceAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PricesFragment : Fragment() {
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var infoService: InfoService
    private lateinit var recyclerView: android.support.v7.widget.RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_prices, container, false)

        val retrofit = RestApi.getInstance()
        infoService = retrofit.create(InfoService::class.java)

        recyclerView = v.findViewById(R.id.recyclerPrices)
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipe = v.findViewById(R.id.swipePrices)
        swipe.setOnRefreshListener {
            addItems()
            //swipe.isRefreshing = false
        }

        addItems()

        return v
    }

    @SuppressLint("CheckResult")
    private fun addItems() {
        infoService.getPrices("gnomes")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<PriceResponse>() {
                override fun onSuccess(t: PriceResponse) {
                    val prices = ArrayList<Currency>()
                    prices.add(t.btc)
                    prices.add(t.ltc)
                    displayData(prices)
                    swipe.isRefreshing = false
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, "Ошибка при загрузке: " + e.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun displayData(prices: ArrayList<Currency>) {
        val adapter = PriceAdapter(prices, context!!)
        recyclerView.adapter = adapter
    }
}