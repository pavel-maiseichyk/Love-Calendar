package com.paulmais.lovecalendar.presentation.calendar

import com.paulmais.lovecalendar.presentation.home.components.DaysUntilItemUI
import kotlinx.datetime.LocalDate

data class CalendarState(
    val monthData: MonthData = MonthData(),
    val meetings: List<LocalDate> = emptyList(),
    val specialDayNumber: Int = 0,
    val isInEditMode: Boolean = false,
    val daysLeftText: String = "...",
    val daysUntilUIList: List<DaysUntilItemUI> = emptyList(),
    val isLoading: Boolean = true
)

