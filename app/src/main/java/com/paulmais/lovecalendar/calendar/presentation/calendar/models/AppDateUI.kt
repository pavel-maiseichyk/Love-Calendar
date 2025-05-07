package com.paulmais.lovecalendar.calendar.presentation.calendar.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.paulmais.lovecalendar.calendar.domain.model.AppDate
import com.paulmais.lovecalendar.calendar.domain.model.DateType
import com.paulmais.lovecalendar.calendar.domain.model.DateType.*
import com.paulmais.lovecalendar.core.presentation.ui.theme.blue
import com.paulmais.lovecalendar.core.presentation.ui.theme.blue_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.futureMeetingColor
import com.paulmais.lovecalendar.core.presentation.ui.theme.green
import com.paulmais.lovecalendar.core.presentation.ui.theme.green_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.pastMeetingColor
import com.paulmais.lovecalendar.core.presentation.ui.theme.red
import com.paulmais.lovecalendar.core.presentation.ui.theme.red_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.specialColor
import com.paulmais.lovecalendar.core.presentation.ui.theme.yellow
import com.paulmais.lovecalendar.core.presentation.ui.theme.yellow_container
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Immutable
data class AppDateUI(
    val date: LocalDate = LocalDate(year = 1975, month = Month(1), dayOfMonth = 1),
    val types: Set<DateType> = setOf(),
    val containerColor: Color = Color.White,
    val contentColor: Color = Color.Black,
    val mainIndicatorColor: Color? = null,
    val additionalIndicatorColor: Color? = null
)

fun AppDate.toAppDateUI(): AppDateUI {
    val containerColor = when {
        TODAY in types -> yellow_container
        SPECIAL in types -> red_container
        MEETING in types && FUTURE in types -> green_container
        MEETING in types && PAST in types -> blue_container
        else -> Color.Transparent
    }

    val contentColor = when {
        TODAY in types -> yellow
        SPECIAL in types -> red
        MEETING in types && FUTURE in types -> green
        MEETING in types && PAST in types -> blue
        else -> dark_gray
    }

    val mainIndicatorColor = when {
        PAST in types && MEETING in types && SPECIAL in types -> pastMeetingColor
        TODAY in types && MEETING in types -> futureMeetingColor
        SPECIAL in types && MEETING in types -> futureMeetingColor
        else -> null
    }

    val additionalIndicatorColor =
        if (TODAY in types && SPECIAL in types) specialColor else null

    return AppDateUI(
        date = date,
        types = types,
        containerColor = containerColor,
        contentColor = contentColor,
        mainIndicatorColor = mainIndicatorColor,
        additionalIndicatorColor = additionalIndicatorColor
    )
}
