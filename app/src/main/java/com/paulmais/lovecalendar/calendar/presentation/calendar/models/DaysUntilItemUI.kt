package com.paulmais.lovecalendar.calendar.presentation.calendar.models

import com.paulmais.lovecalendar.calendar.domain.model.DaysUntilItem
import com.paulmais.lovecalendar.calendar.domain.model.DaysUntilType
import kotlinx.datetime.LocalDate

data class DaysUntilItemUI(
    val title: String,
    val daysUntil: String,
    val date: LocalDate,
    val type: DaysUntilType,
    val isShowingDate: Boolean
)

fun DaysUntilItem.toDaysUntilItemUI(): DaysUntilItemUI {
    return DaysUntilItemUI(
        title = title,
        daysUntil = daysUntil.toString(),
        date = date,
        type = type,
        isShowingDate = false
    )
}
