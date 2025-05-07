package com.paulmais.lovecalendar.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.paulmais.lovecalendar.MainViewModel
import com.paulmais.lovecalendar.app.LoveCalendarApp
import com.paulmais.lovecalendar.auth.data.AuthRepositoryImpl
import com.paulmais.lovecalendar.auth.data.EncryptedSessionStorage
import com.paulmais.lovecalendar.auth.data.model.AuthInfoSerializable
import com.paulmais.lovecalendar.auth.data.model.AuthInfoSerializer
import com.paulmais.lovecalendar.auth.domain.AuthRepository
import com.paulmais.lovecalendar.auth.domain.SessionStorage
import com.paulmais.lovecalendar.auth.presentation.AuthViewModel
import com.paulmais.lovecalendar.calendar.data.OfflineFirstUserRepository
import com.paulmais.lovecalendar.calendar.data.local.DataStoreUserPrefs
import com.paulmais.lovecalendar.calendar.data.local.MeetingsDB
import com.paulmais.lovecalendar.calendar.data.local.RoomMeetingsDataSource
import com.paulmais.lovecalendar.calendar.data.local.dao.MeetingsDao
import com.paulmais.lovecalendar.calendar.data.remote.KtorUserDataSource
import com.paulmais.lovecalendar.calendar.domain.repository.LocalMeetingsDataSource
import com.paulmais.lovecalendar.calendar.domain.repository.RemoteUserDataSource
import com.paulmais.lovecalendar.calendar.domain.repository.UserPrefs
import com.paulmais.lovecalendar.calendar.domain.repository.UserRepository
import com.paulmais.lovecalendar.calendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.calendar.domain.use_case.GenerateDaysLeft
import com.paulmais.lovecalendar.calendar.presentation.calendar.CalendarViewModel
import com.paulmais.lovecalendar.calendar.presentation.settings.SettingsViewModel
import com.paulmais.lovecalendar.core.data.remote.HttpClientFactory
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
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

    single<DataStore<Preferences>>(named("userPreferencesDataStore")) {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(androidContext(), "user_prefs")),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().preferencesDataStoreFile("user_preferences") }
        )
    }

    single<DataStore<AuthInfoSerializable>>(named("authInfoDataStore")) {
        DataStoreFactory.create(
            serializer = AuthInfoSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().dataStoreFile("auth_info_store") }
        )
    }

    single<SessionStorage> {
        EncryptedSessionStorage(get(named("authInfoDataStore")))
    }

    single<UserPrefs> {
        DataStoreUserPrefs(get(named("userPreferencesDataStore")))
    }

    single<HttpClient> {
        HttpClientFactory(get()).build()
    }

    single<MeetingsDao> {
        val meetingsDB = get<MeetingsDB>()
        meetingsDB.getMeetingsDao()
    }

    single<CoroutineScope> {
        (androidApplication() as LoveCalendarApp).applicationScope
    }

    singleOf(::GenerateDates)
    singleOf(::GenerateDaysLeft)

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    singleOf(::RoomMeetingsDataSource).bind<LocalMeetingsDataSource>()
    singleOf(::KtorUserDataSource).bind<RemoteUserDataSource>()

    singleOf(::OfflineFirstUserRepository).bind<UserRepository>()

    viewModelOf(::MainViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::CalendarViewModel)
    viewModelOf(::SettingsViewModel)
}