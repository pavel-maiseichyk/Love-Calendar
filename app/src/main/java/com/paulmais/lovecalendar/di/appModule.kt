package com.paulmais.lovecalendar.di

import androidx.room.Room
import com.paulmais.lovecalendar.data.MeetingsDB
import com.paulmais.lovecalendar.data.MeetingsDao
import com.paulmais.lovecalendar.data.repository.MeetingDataSourceImpl
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.presentation.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<MeetingsDB> {
        Room.databaseBuilder(
            androidContext(),
            MeetingsDB::class.java,
            "meetings"
        ).build()
    }

    single<MeetingsDao> {
        val meetingsDB = get<MeetingsDB>()
        meetingsDB.getMeetingsDao()
    }

    singleOf(::GenerateDates)

    singleOf(::MeetingDataSourceImpl).bind<MeetingsDataSource>()

    viewModelOf(::HomeViewModel)
}