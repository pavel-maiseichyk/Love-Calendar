package com.paulmais.lovecalendar.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface UserPrefs {
    fun getStartingDate(): Flow<LocalDate?>
    suspend fun updateStartingDate(date: LocalDate)
}