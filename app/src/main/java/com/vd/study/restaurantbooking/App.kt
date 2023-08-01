package com.vd.study.restaurantbooking

import android.app.Application
import com.vd.study.restaurantbooking.di.AppComponent
import com.vd.study.restaurantbooking.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(applicationContext).build()
    }
}