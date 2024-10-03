package com.paulmais.lovecalendar.presentation.home

import com.paulmais.lovecalendar.domain.model.AppDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class HomeState(
    val firstMonth: Month? = null,
    val secondMonth: Month? = null,
    val firstYear: Int = 0,
    val secondYear: Int = 0,
    val firstMonthDates: List<AppDate> = emptyList(),
    val secondMonthDates: List<AppDate> = emptyList(),

    // Positions in a week range from 0 to 6
    val firstMonthFirstDayOfWeekPosition: Int = 0,
    val secondMonthFirstDayOfWeekPosition: Int = 0,

    // Empty dates are the cells without a date left in the last week on a month
    val firstMonthEmptyDatesAmount: Int = 0,
    val secondMonthEmptyDatesAmount: Int = 0,

    val today: LocalDate? = null,
    val isInEditMode: Boolean = false,
    val daysLeftText: String = "",
    val meetings: List<LocalDate> = emptyList(),
    val isLoading: Boolean = true
)

