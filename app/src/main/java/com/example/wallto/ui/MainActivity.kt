/*
 * Created by Mark Abramenko on 15.09.19 15:25
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 15.09.19 15:12
 */

package com.example.wallto.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.example.wallto.R
import com.example.wallto.ui.main.home.HomeFragment
import com.example.wallto.ui.main.prices.PricesFragment
import com.example.wallto.ui.main.SettingsFragment
import com.example.wallto.ui.main.WalletsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var btv: BottomNavigationView
    private lateinit var prefs: SharedPreferences
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        btv = findViewById(R.id.bottom_navigation)
        btv.setOnNavigationItemSelectedListener(navListener)
        btv.setOnNavigationItemReselectedListener(navReListener)

        // Поддержка тулбара
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.hide()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, HomeFragment())
            .commit()
    }

    private val navReListener = BottomNavigationView.OnNavigationItemReselectedListener {
        //TODO Обработка действий на перевыбранный пункт меню
        when (it.itemId) {
            R.id.nav_home -> {

            }
            R.id.nav_wallets -> {

            }
            R.id.nav_charts -> {

            }
            R.id.nav_settings -> {

            }
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        lateinit var selectedFragment: Fragment
        when (it.itemId) {
            R.id.nav_home -> {
                selectedFragment = HomeFragment()
            }
            R.id.nav_wallets -> {
                selectedFragment = WalletsFragment()
            }
            R.id.nav_charts -> {
                selectedFragment = PricesFragment()
            }
            R.id.nav_settings -> {
                selectedFragment = SettingsFragment()
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, selectedFragment)
            .commit()

        true
    }
}
