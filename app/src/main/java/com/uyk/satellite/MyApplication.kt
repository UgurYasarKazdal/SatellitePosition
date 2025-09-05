package com.uyk.satellite

import android.app.Application
import com.uyk.satellite.di.myModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            // Android context
            androidContext(this@MyApplication)
            // Add your modules
            modules(myModules)
        }
    }
}