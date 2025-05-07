package com.paulmais.lovecalendar.calendar.data.local

import android.database.sqlite.SQLiteFullException
import com.paulmais.lovecalendar.calendar.data.local.dao.MeetingsDao
import com.paulmais.lovecalendar.calendar.data.mapper.toLocalDate
import com.paulmais.lovecalendar.calendar.data.mapper.toMeetingEntity
import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.calendar.domain.repository.LocalMeetingsDataSource
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.Result
import com.paulmais.lovecalendar.core.domain.asEmptyDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class RoomMeetingsDataSource(
    private val meetingsDao: MeetingsDao
) : LocalMeetingsDataSource {

    override fun getMeetings(): Flow<List<LocalDate>> {
        return meetingsDao.getMeetings().map { list -> list.map { it.toLocalDate() } }
    }

    override fun getPendingSyncMeetings(): Flow<List<LocalDate>> {
        return meetingsDao.getPendingSyncMeetings()
            .map { list -> list.map { it.toLocalDate() } }
    }

    override suspend fun updateMeetings(meetings: List<LocalDate>): EmptyResult<DataError.Local> {
        return try {
            val result =
                meetingsDao.updateMeetings(meetings = meetings.map { it.toMeetingEntity() })
            Result.Success(result).asEmptyDataResult()
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun updateAllMeetingSyncStatus(status: SyncStatus): EmptyResult<DataError.Local> {
        return try {
            val result = meetingsDao.updateAllMeetingSyncStatus(status.name)
            Result.Success(result).asEmptyDataResult()
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteAllMeetings(): EmptyResult<DataError.Local> {
        return try {
            val result = meetingsDao.clearMeetings()
            Result.Success(result).asEmptyDataResult()
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}