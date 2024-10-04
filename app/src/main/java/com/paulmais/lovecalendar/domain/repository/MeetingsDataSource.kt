package com.paulmais.lovecalendar.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface MeetingsDataSource {
    fun getMeetings(): Flow<List<LocalDate>>
    suspend fun updateMeetings(meetings: List<LocalDate>)
}