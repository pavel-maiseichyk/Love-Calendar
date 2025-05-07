package com.paulmais.lovecalendar.util

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.paulmais.lovecalendar.MainViewModel
import com.paulmais.lovecalendar.auth.data.AuthRepositoryImpl
import com.paulmais.lovecalendar.auth.data.EncryptedSessionStorage
import com.paulmais.lovecalendar.auth.domain.AuthRepository
import com.paulmais.lovecalendar.auth.domain.SessionStorage
import com.paulmais.lovecalendar.auth.presentation.AuthViewModel
import com.paulmais.lovecalendar.calendar.data.local.MeetingsDB
import com.paulmais.lovecalendar.core.data.remote.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val testAppModule = module {
    single {
        Room.inMemoryDatabaseBuilder(
            androidContext(),
            MeetingsDB::class.java
        ).allowMainThreadQueries().build()
    }

    single<HttpClient> {
        HttpClientFactory(get()).build()
    }

    single<SharedPreferences> {
        androidApplication().getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    viewModelOf(::AuthViewModel)
    viewModelOf(::MainViewModel)
}