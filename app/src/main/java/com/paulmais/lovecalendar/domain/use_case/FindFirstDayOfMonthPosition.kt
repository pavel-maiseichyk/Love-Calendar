package com.paulmais.lovecalendar.domain.use_case

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber

class FindFirstDayOfMonthPosition {

    fun execute(
        month: Month,
        year: Int
    ): Int {
        return LocalDate(year = year, month = month, dayOfMonth = 1).dayOfWeek.isoDayNumber - 1
    }
}