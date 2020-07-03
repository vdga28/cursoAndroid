package com.example.demoandroid

import android.app.Application
import com.example.demoandroid.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class DemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            koinApplication { this@DemoApplication }
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}