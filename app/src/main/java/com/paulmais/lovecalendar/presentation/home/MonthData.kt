package com.paulmais.lovecalendar.presentation.home

import com.paulmais.lovecalendar.domain.model.AppDate
import kotlinx.datetime.Month

data class MonthData(
    val month: Month = Month(1),
    val year: Int = 0,
    val dates: List<AppDate> = emptyList(),
    val firstDayOfWeekPosition: Int = 0, // Positions in a week range from 0 to 6
    val emptyDatesAmount: Int = 0 // Cells without a date left in the last week on a month
)