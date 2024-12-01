package com.paulmais.lovecalendar.domain.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

object DateUtil {

    fun now(): LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun nowAtStartOfMonth(): LocalDate = LocalDate(
        dayOfMonth = 1,
        month = now().month,
        year = now().year
    )

    fun localDateAtStartOfMonth(month: Month, year: Int): LocalDate = LocalDate(
        dayOfMonth = 1,
        month = month,
        year = year
    )

    fun localDateAtStartOfMonth(date: LocalDate): LocalDate = LocalDate(
        dayOfMonth = 1,
        month = date.month,
        year = date.year
    )

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

    fun String.formatToISO(): LocalDate {
        if (length != 8) {
            throw IllegalArgumentException("Invalid input. Expected a string with 8 characters.")
        }

        val day = substring(0, 2)
        val month = substring(2, 4)
        val year = substring(4, 8)

        val formattedDate = "$year-$month-${day}"

        return LocalDate.parse(formattedDate)
    }

    fun LocalDate?.reverseDateFormat(): String {
        if (this == null) {
            throw IllegalArgumentException("Invalid input. LocalDate cannot be null.")
        }

        val dateString = this.toString()

        val year = dateString.substring(0, 4)
        val month = dateString.substring(5, 7)
        val day = dateString.substring(8, 10)

        return "$day.$month.$year"
    }
}