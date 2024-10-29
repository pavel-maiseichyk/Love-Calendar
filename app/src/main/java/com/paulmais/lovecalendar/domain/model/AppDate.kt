package com.paulmais.lovecalendar.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class AppDate(
    val date: LocalDate = LocalDate(year = 1975, month = Month(1), dayOfMonth = 1),
    val types: Set<DateType> = setOf(DateType.FUTURE)
)

enum class DateType {
    TODAY, PAST, FUTURE, MEETING, SPECIAL
}