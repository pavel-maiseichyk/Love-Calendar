package com.paulmais.lovecalendar.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paulmais.lovecalendar.domain.repository.UserPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

class DataStoreUserPrefs(
    private val dataStore: DataStore<Preferences>
) : UserPrefs {

    companion object {
        val ANNIVERSARY_KEY = stringPreferencesKey("anniversary_key")
    }

    override fun getStartingDate(): Flow<LocalDate?> {
        return dataStore.data.map { prefs -> prefs[ANNIVERSARY_KEY]?.toLocalDate() }
    }

    override suspend fun updateStartingDate(date: LocalDate) {
        dataStore.edit { prefs ->
            prefs[ANNIVERSARY_KEY] = date.toString()
        }
    }
}