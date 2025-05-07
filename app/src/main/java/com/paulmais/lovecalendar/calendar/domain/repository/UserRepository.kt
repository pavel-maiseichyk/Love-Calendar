package com.paulmais.lovecalendar.calendar.domain.repository

import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface UserRepository {
    fun getMeetings(): Flow<List<LocalDate>>
    fun getStartingDate(): Flow<LocalDate?>

    fun getSyncStatus(): Flow<SyncStatus>

    suspend fun fetchUser(): EmptyResult<DataError>
    suspend fun updateMeetings(meetings: List<LocalDate>): EmptyResult<DataError>
    suspend fun updateUserProfile(
        name: String? = null,
        email: String? = null,
        specialDate: LocalDate? = null,
    ): EmptyResult<DataError>

    suspend fun syncWithRemote(): EmptyResult<DataError>
    suspend fun logout(): EmptyResult<DataError>
}