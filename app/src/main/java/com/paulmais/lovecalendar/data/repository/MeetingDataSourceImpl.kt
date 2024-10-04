package com.paulmais.lovecalendar.data.repository

import com.paulmais.lovecalendar.data.MeetingsDao
import com.paulmais.lovecalendar.data.mapper.toLocalDate
import com.paulmais.lovecalendar.data.mapper.toMeetingEntity
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class MeetingDataSourceImpl(
    private val meetingsDao: MeetingsDao
) : MeetingsDataSource {

    override fun getMeetings(): Flow<List<LocalDate>> {
        return meetingsDao.getMeetings().map { list -> list.map { it.toLocalDate() } }
    }

    override suspend fun updateMeetings(meetings: List<LocalDate>) {
        meetingsDao.updateMeetings(meetings = meetings.map { it.toMeetingEntity() })
    }
}