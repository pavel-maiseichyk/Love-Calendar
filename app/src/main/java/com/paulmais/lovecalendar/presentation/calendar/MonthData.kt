package com.paulmais.lovecalendar.presentation.calendar

import kotlinx.datetime.Month

data class MonthData(
    val month: Month = Month(1),
    val year: Int = 0,
    val dates: List<AppDateUI> = emptyList(),
    val firstDayOfWeekPosition: Int = 0, // Positions in a week range from 0 to 6
)