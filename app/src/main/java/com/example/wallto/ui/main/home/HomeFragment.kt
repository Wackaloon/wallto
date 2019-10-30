/*
 * Created by Mark Abramenko on 15.09.19 21:21
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 15.09.19 15:31
 */

package com.example.wallto.ui.main.home

import android.animation.ValueAnimator
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallto.R
import com.example.wallto.network.services.InfoService
import com.example.wallto.ui.MainActivity
import com.example.wallto.utils.PrefsRepository
import com.github.florent37.shapeofview.shapes.ArcView

class HomeFragment : Fragment(), HomeView {
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var progress: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var arcView: ArcView
    private lateinit var scrollView: ScrollView
    private var TAG = this.javaClass.simpleName

    private var presenter = HomePresenter(this)

    private lateinit var hello: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)
        hello = v.findViewById(R.id.tvHello)
        scrollView = v.findViewById(R.id.scrollWallet)
        arcView = v.findViewById(R.id.arcView)

        presenter.initNetwork()
        presenter.initUserData()

        scrollView.viewTreeObserver.addOnScrollChangedListener(onScrollChangedListener)
        return v
    }
    private val onScrollChangedListener = ViewTreeObserver.OnScrollChangedListener {
        val scrollY: Int = scrollView.scrollY
        writeLog("scrollY")
        presenter.notifyScrollChanged(scrollY)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val act = activity as MainActivity
        act.supportActionBar!!.hide()
    }

    override fun writeLog(mes: String?) {
        Log.e(TAG, mes)
    }

    override fun writeLog(mes: String?, e: Throwable?) {
        Log.e(TAG, mes, e)
    }

    override fun showLogin(name: String) {
        // а зачем name передается как параметр?
        hello.text = "Доброго времени суток, username"
    }

    override fun showDecrementAnimation(baseHeight: Int) {
        ValueAnimator.ofInt(baseHeight, 0).apply {
            addUpdateListener { arcView.arcHeight = it.animatedValue as Int }
            duration = 400
        }.start()
    }

    override fun setArcValue(baseHeight: Int) {
        arcView.arcHeight = baseHeight
    }

    override fun showIncrementAnimation(baseHeight: Int) {
        // magic numbers самая злобная вещь, потом пойди вспомни что это за 80 и можно ли его менять
        // хорошая практика выносить такие вещи в константы и писать очень понятное название и еще
        // достаточно длинное пояснение что это и зачем оно нужно
        ValueAnimator.ofInt(baseHeight, 80, 65, 80).apply {
            addUpdateListener { arcView.arcHeight = it.animatedValue as Int }
            duration = 500
        }.start()
    }
}
