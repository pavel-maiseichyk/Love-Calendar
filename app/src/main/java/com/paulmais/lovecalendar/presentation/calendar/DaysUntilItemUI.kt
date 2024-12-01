package com.paulmais.lovecalendar.presentation.calendar

import com.paulmais.lovecalendar.domain.model.DaysUntilItem
import com.paulmais.lovecalendar.domain.model.DaysUntilType
import com.paulmais.lovecalendar.domain.util.DateUtil.reverseDateFormat
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
