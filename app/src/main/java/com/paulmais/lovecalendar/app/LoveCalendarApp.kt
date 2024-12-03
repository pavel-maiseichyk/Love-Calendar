package com.paulmais.lovecalendar.app

import android.app.Application
import com.paulmais.lovecalendar.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LoveCalendarApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LoveCalendarApp)
            modules(appModule)
        }
    }
}