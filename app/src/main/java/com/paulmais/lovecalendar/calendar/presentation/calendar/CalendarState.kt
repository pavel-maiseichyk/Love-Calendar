package com.paulmais.lovecalendar.calendar.presentation.calendar

import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.calendar.presentation.calendar.models.AppDateUI
import com.paulmais.lovecalendar.calendar.presentation.calendar.models.DaysUntilItemUI
import kotlinx.datetime.Month

data class CalendarState(
    val isLoading: Boolean = true,
    val syncStatus: SyncStatus = SyncStatus.Pending,
    val isInEditMode: Boolean = false,
    val month: Month = Month(1),
    val year: Int = 0,
    val appDateUIList: List<AppDateUI> = emptyList(),
    val firstDayOfWeekPosition: Int = 0, // Positions in a week range from 0 to 6
    val daysLeftText: String = "...",
    val daysUntilUIList: List<DaysUntilItemUI> = emptyList(),
)