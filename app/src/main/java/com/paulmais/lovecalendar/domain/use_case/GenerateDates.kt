package com.paulmais.lovecalendar.domain.use_case

import com.paulmais.lovecalendar.domain.model.AppDate
import com.paulmais.lovecalendar.domain.model.DateType
import com.paulmais.lovecalendar.domain.util.DateUtil
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

class GenerateDates {

    fun execute(
        now: LocalDate,
        month: Month,
        year: Int,
        meetings: List<LocalDate>,
        specialDayNumber: Int
    ): List<AppDate> {
        val days = DateUtil.daysInMonth(month = month, year = year)
        val dates = MutableList(days) { AppDate() }

        for (day in 1..days) {
            val currentDate = LocalDate(
                year = year,
                month = month,
                dayOfMonth = day
            )

            val type = if (meetings.contains(currentDate)) {
                when {
                    currentDate < now -> DateType.PAST_MEETING
                    currentDate > now && currentDate.dayOfMonth == specialDayNumber -> DateType.SPECIAL_MEETING
                    currentDate > now -> DateType.FUTURE_MEETING
                    currentDate == now && currentDate.dayOfMonth == specialDayNumber -> DateType.SPECIAL
                    currentDate == now -> DateType.TODAY_MEETING
                    else -> DateType.TODAY_MEETING
                }
            } else {
                when {
                    currentDate.dayOfMonth == specialDayNumber -> DateType.SPECIAL
                    currentDate == now -> DateType.TODAY
                    currentDate < now -> DateType.PAST
                    else -> DateType.NORMAL
                }
            }

            dates[day - 1] = AppDate(
                date = currentDate,
                type = type
            )
        }
        return dates
    }
}