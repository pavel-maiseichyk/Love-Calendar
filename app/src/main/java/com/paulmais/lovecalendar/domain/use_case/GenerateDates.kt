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

            val types = buildSet {
                if (currentDate == now) add(DateType.TODAY)
                else if (currentDate < now) add(DateType.PAST)
                else add(DateType.FUTURE)

                if (meetings.contains(currentDate)) add(DateType.MEETING)
                if (currentDate.dayOfMonth == specialDayNumber) add(DateType.SPECIAL)

                if (isEmpty()) add(DateType.FUTURE)
            }

            dates[day - 1] = AppDate(
                date = currentDate,
                types = types
            )
        }
        return dates
    }
}