package com.paulmais.lovecalendar.calendar.domain.repository

import com.paulmais.lovecalendar.calendar.domain.model.SyncInfo
import com.paulmais.lovecalendar.calendar.domain.model.User
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface UserPrefs {
    suspend fun getUserWithoutMeetings(): Result<User, DataError.Local>
    suspend fun clearUserData(): EmptyResult<DataError.Local>

    suspend fun updateUserData(
        id: String? = null,
        name: String? = null,
        email: String? = null,
        startingDate: String? = null
    ): EmptyResult<DataError.Local>

    fun getStartingDate(): Flow<LocalDate?>
    fun getSyncInfo(): Flow<SyncInfo>
    suspend fun updateSyncInfo(syncInfo: SyncInfo): EmptyResult<DataError.Local>
}