package com.paulmais.lovecalendar.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.paulmais.lovecalendar.data.MeetingsDB
import com.paulmais.lovecalendar.data.MeetingsDao
import com.paulmais.lovecalendar.data.repository.DataStoreUserPrefs
import com.paulmais.lovecalendar.data.repository.MeetingDataSourceImpl
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.repository.UserPrefs
import com.paulmais.lovecalendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.domain.use_case.GenerateDaysLeft
import com.paulmais.lovecalendar.presentation.calendar.CalendarViewModel
import com.paulmais.lovecalendar.presentation.home.HomeViewModel
import com.paulmais.lovecalendar.presentation.settings.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val USER_PREFERENCES = "user_preferences"

val appModule = module {
    single<MeetingsDB> {
        Room.databaseBuilder(
            androidContext(),
            MeetingsDB::class.java,
            "meetings"
        ).build()
    }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(androidContext(), USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    single<MeetingsDao> {
        val meetingsDB = get<MeetingsDB>()
        meetingsDB.getMeetingsDao()
    }

    singleOf(::GenerateDates)
    singleOf(::GenerateDaysLeft)

    singleOf(::MeetingDataSourceImpl).bind<MeetingsDataSource>()
    singleOf(::DataStoreUserPrefs).bind<UserPrefs>()

    viewModelOf(::CalendarViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::HomeViewModel)
}