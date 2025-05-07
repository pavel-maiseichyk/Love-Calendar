package com.paulmais.lovecalendar.app

import android.app.Application
import com.paulmais.lovecalendar.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LoveCalendarApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LoveCalendarApp)
            modules(appModule)
        }
    }
}