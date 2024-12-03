package com.paulmais.lovecalendar.presentation.calendar

import kotlinx.datetime.Month

data class CalendarState(
    val isLoading: Boolean = true,
    val isInEditMode: Boolean = false,
    val month: Month = Month(1),
    val year: Int = 0,
    val appDateUIList: List<AppDateUI> = emptyList(),
    val firstDayOfWeekPosition: Int = 0, // Positions in a week range from 0 to 6
    val daysLeftText: String = "...",
    val daysUntilUIList: List<DaysUntilItemUI> = emptyList(),
)

