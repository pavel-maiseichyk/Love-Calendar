package com.paulmais.lovecalendar.presentation.calendar

import kotlinx.datetime.LocalDate

data class CalendarState(
    val firstMonthData: MonthData = MonthData(),
    val secondMonthData: MonthData = MonthData(),
    val meetings: List<LocalDate> = emptyList(),
    val specialDayNumber: Int = 0,
    val isInEditMode: Boolean = false,
    val daysLeftText: String = "...",
    val isLoading: Boolean = true
)

