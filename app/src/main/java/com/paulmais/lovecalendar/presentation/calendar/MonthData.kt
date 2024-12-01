package com.paulmais.lovecalendar.presentation.calendar

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class MonthData(
    val month: Month = Month(1),
    val year: Int = 0,
    val dates: List<AppDateUI> = emptyList(),
    val firstDayOfWeekPosition: Int = 0, // Positions in a week range from 0 to 6
    val dateAtStartOfMonth: LocalDate = LocalDate(year = 0, monthNumber = 1, dayOfMonth = 1)
)