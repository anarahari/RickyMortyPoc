package com.compose.rickymortypoc

import android.app.Application
import com.compose.rickymortypoc.di.component.AppComponent
import com.compose.rickymortypoc.di.component.DaggerAppComponent

class BaseApplication : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
    }
}
