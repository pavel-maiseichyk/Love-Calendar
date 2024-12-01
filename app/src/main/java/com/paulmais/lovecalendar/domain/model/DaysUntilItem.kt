package com.paulmais.lovecalendar.domain.model

import kotlinx.datetime.LocalDate

data class DaysUntilItem(
    val title: String,
    val daysUntil: Int,
    val date: LocalDate,
    val type: DaysUntilType
)

enum class DaysUntilType {
    Today, Meeting, Special, Other
}
