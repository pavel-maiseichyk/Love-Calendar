package com.paulmais.lovecalendar.domain.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

object DateUtil {

    fun now(): LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun daysInMonth(month: Month, year: Int): Int {
        return if (month.number != 12)
            LocalDate(
                year = year,
                monthNumber = month.number + 1,
                dayOfMonth = 1
            ).toEpochDays() -
                    LocalDate(
                        year = year,
                        month = month,
                        dayOfMonth = 1
                    ).toEpochDays()
        else 31
    }

    fun daysDiff(nextMeeting: LocalDate?, now: LocalDate): Int {
        if (nextMeeting == null) return -1
        if (nextMeeting.month.number > now.month.number && nextMeeting.dayOfMonth > now.dayOfMonth) {
            return nextMeeting.minus(now).days + daysInMonth(now.month, now.year)
        }
        if (nextMeeting.month.number > now.month.number && nextMeeting.dayOfMonth == now.dayOfMonth) {
            return daysInMonth(now.month, now.year)
        }
        return nextMeeting.minus(now).days
    }
}