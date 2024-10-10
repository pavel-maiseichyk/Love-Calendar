package com.paulmais.lovecalendar.domain.model

data class DaysUntilItem(
    val title: String,
    val daysUntil: Int,
    val type: DaysUntilType
)

enum class DaysUntilType {
    Meeting, Special, Other
}
