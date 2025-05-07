package com.paulmais.lovecalendar.calendar.domain.repository

import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface LocalMeetingsDataSource {
    fun getMeetings(): Flow<List<LocalDate>>
    fun getPendingSyncMeetings(): Flow<List<LocalDate>>
    suspend fun updateMeetings(meetings: List<LocalDate>): EmptyResult<DataError.Local>
    suspend fun updateAllMeetingSyncStatus(status: SyncStatus): EmptyResult<DataError.Local>
    suspend fun deleteAllMeetings(): EmptyResult<DataError.Local>
}