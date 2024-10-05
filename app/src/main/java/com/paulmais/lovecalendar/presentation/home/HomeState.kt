package com.paulmais.lovecalendar.presentation.home

import kotlinx.datetime.LocalDate

data class HomeState(
    val firstMonthData: MonthData = MonthData(),
    val secondMonthData: MonthData = MonthData(),
    val meetings: List<LocalDate> = emptyList(),
    val isInEditMode: Boolean = false,
    val daysLeftText: String = "...",
    val isLoading: Boolean = true
)

