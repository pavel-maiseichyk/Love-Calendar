package com.paulmais.lovecalendar.calendar.domain.repository

import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDate

class FakeUserRepository : UserRepository {

    var meetings = MutableStateFlow<List<LocalDate>>(emptyList())
    var startingDate = MutableStateFlow<LocalDate?>(null)
    var syncStatus = MutableStateFlow(SyncStatus.Synced)
    var error: DataError? = null

    private fun result() = error?.let { Result.Error(it) } ?: Result.Success(Unit)

    override fun getMeetings(): Flow<List<LocalDate>> {
        return meetings
    }

    override fun getStartingDate(): Flow<LocalDate?> {
        return startingDate
    }

    override fun getSyncStatus(): Flow<SyncStatus> {
        return syncStatus
    }

    override suspend fun fetchUser(): EmptyResult<DataError> {
        return result()
    }

    override suspend fun updateMeetings(meetings: List<LocalDate>): EmptyResult<DataError> {
        this.meetings.value = meetings
        return result()
    }

    override suspend fun updateUserProfile(
        name: String?,
        email: String?,
        specialDate: LocalDate?
    ): EmptyResult<DataError> {
        startingDate.value = specialDate
        return result()
    }

    override suspend fun syncWithRemote(): EmptyResult<DataError> {
        return result()
    }

    override suspend fun logout(): EmptyResult<DataError> {
        return result()
    }
}