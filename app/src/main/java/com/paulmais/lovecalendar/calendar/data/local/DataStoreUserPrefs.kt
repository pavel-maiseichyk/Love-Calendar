package com.paulmais.lovecalendar.calendar.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paulmais.lovecalendar.calendar.domain.model.SyncInfo
import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.calendar.domain.model.User
import com.paulmais.lovecalendar.calendar.domain.repository.UserPrefs
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.Result
import com.paulmais.lovecalendar.core.domain.asEmptyDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class DataStoreUserPrefs(
    private val dataStore: DataStore<Preferences>
) : UserPrefs {

    companion object {
        val ANNIVERSARY_KEY = stringPreferencesKey("anniversary_key")
        val ID_KEY = stringPreferencesKey("id_key")
        val NAME_KEY = stringPreferencesKey("name_key")
        val EMAIL_KEY = stringPreferencesKey("email_key")

        val NAME_SYNC_KEY = stringPreferencesKey("name_sync_key")
        val EMAIL_SYNC_KEY = stringPreferencesKey("email_sync_key")
        val ANNIVERSARY_SYNC_KEY = stringPreferencesKey("anniversary_sync_key")
    }

    override suspend fun getUserWithoutMeetings(): Result<User, DataError.Local> {
        return try {
            val preferences = dataStore.data.first()
            val id = preferences[ID_KEY]
            val email = preferences[EMAIL_KEY]
            val name = preferences[NAME_KEY]
            val specialDate = preferences[ANNIVERSARY_KEY]

            if (id == null || email == null) {
                Result.Error(DataError.Local.MISSING_DATA)
            } else {
                Result.Success(
                    User(
                        id = id,
                        email = email,
                        name = name ?: "",
                        specialDate = if (!specialDate.isNullOrEmpty()) LocalDate.parse(specialDate) else null,
                        meetings = emptyList()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun clearUserData(): EmptyResult<DataError.Local> {
        return try {
            dataStore.edit { prefs ->
                prefs.remove(ID_KEY)
                prefs.remove(NAME_KEY)
                prefs.remove(EMAIL_KEY)
                prefs.remove(ANNIVERSARY_KEY)
                prefs.remove(NAME_SYNC_KEY)
                prefs.remove(EMAIL_SYNC_KEY)
                prefs.remove(ANNIVERSARY_SYNC_KEY)
            }
            Result.Success(Unit).asEmptyDataResult()
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateUserData(
        id: String?,
        name: String?,
        email: String?,
        startingDate: String?
    ): EmptyResult<DataError.Local> {
        return try {
            dataStore.edit { prefs ->
                id?.let { prefs[ID_KEY] = it }
                if (name != null && name != prefs[NAME_KEY]) {
                    prefs[NAME_KEY] = name
                    prefs[NAME_SYNC_KEY] = SyncStatus.Pending.name
                }
                if (email != null && email != prefs[EMAIL_KEY]) {
                    prefs[EMAIL_KEY] = email
                    prefs[EMAIL_SYNC_KEY] = SyncStatus.Pending.name
                }
                if (startingDate != null && startingDate != prefs[ANNIVERSARY_KEY]) {
                    prefs[ANNIVERSARY_KEY] = startingDate
                    prefs[ANNIVERSARY_SYNC_KEY] = SyncStatus.Pending.name
                }
            }
            Result.Success(Unit).asEmptyDataResult()
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun getStartingDate(): Flow<LocalDate?> {
        return dataStore.data.map { prefs ->
            val specialDate = prefs[ANNIVERSARY_KEY]
            if (!specialDate.isNullOrEmpty() && specialDate != "null") LocalDate.parse(specialDate) else null
        }
    }

    override fun getSyncInfo(): Flow<SyncInfo> {
        return dataStore.data.map { prefs ->
            SyncInfo(
                nameSync = prefs[NAME_SYNC_KEY]?.let { SyncStatus.valueOf(it) }
                    ?: SyncStatus.Synced,
                emailSync = prefs[EMAIL_SYNC_KEY]?.let { SyncStatus.valueOf(it) }
                    ?: SyncStatus.Synced,
                anniversarySync = prefs[ANNIVERSARY_SYNC_KEY]?.let { SyncStatus.valueOf(it) }
                    ?: SyncStatus.Synced
            )
        }
    }

    override suspend fun updateSyncInfo(syncInfo: SyncInfo): EmptyResult<DataError.Local> {
        return try {
            dataStore.edit { prefs ->
                prefs[NAME_SYNC_KEY] = syncInfo.nameSync.name
                prefs[EMAIL_SYNC_KEY] = syncInfo.emailSync.name
                prefs[ANNIVERSARY_SYNC_KEY] = syncInfo.anniversarySync.name
            }
            Result.Success(Unit).asEmptyDataResult()
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}