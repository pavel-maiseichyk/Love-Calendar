package com.paulmais.lovecalendar.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class AppDate(
    val date: LocalDate = LocalDate(year = 1975, month = Month(1), dayOfMonth = 1),
    val type: DateType = DateType.NORMAL
)

enum class DateType {
    NORMAL, TODAY, PAST,
    TODAY_MEETING, PAST_MEETING, FUTURE_MEETING,
    SPECIAL, SPECIAL_MEETING, TODAY_SPECIAL, TODAY_MEETING_SPECIAL
}