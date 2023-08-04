package com.example.kodeemployees.app

import android.app.Application
import android.content.Context
import com.example.kodeemployees.di.AppComponent
import com.example.kodeemployees.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .bindContext(this)
            .build()
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }