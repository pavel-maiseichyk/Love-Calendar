package com.paulmais.lovecalendar.presentation.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paulmais.lovecalendar.domain.model.AppDate
import com.paulmais.lovecalendar.domain.model.DateType
import com.paulmais.lovecalendar.domain.model.DateType.FUTURE
import com.paulmais.lovecalendar.domain.model.DateType.MEETING
import com.paulmais.lovecalendar.domain.model.DateType.PAST
import com.paulmais.lovecalendar.domain.model.DateType.SPECIAL
import com.paulmais.lovecalendar.domain.model.DateType.TODAY
import com.paulmais.lovecalendar.presentation.ui.theme.futureMeetingColor
import com.paulmais.lovecalendar.presentation.ui.theme.pastMeetingColor
import com.paulmais.lovecalendar.presentation.ui.theme.specialColor
import com.paulmais.lovecalendar.presentation.ui.theme.todayColor
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Immutable
data class AppDateUI(
    val date: LocalDate = LocalDate(year = 1975, month = Month(1), dayOfMonth = 1),
    val types: Set<DateType> = setOf(),
    val backgroundColor: Color = Color.Black,
    val mainIndicatorColor: Color? = null,
    val additionalIndicatorColor: Color? = null,
    val border: BorderStroke? = null
)

fun AppDate.toAppDateUI(): AppDateUI {
    val backgroundColor = when {
        TODAY in types -> todayColor
        SPECIAL in types -> specialColor
        MEETING in types && FUTURE in types -> futureMeetingColor
        MEETING in types && PAST in types -> pastMeetingColor
        PAST in types -> Color.Transparent
        else -> Color.White
    }
    val mainIndicatorColor = when {
        PAST in types && MEETING in types && SPECIAL in types -> pastMeetingColor
        TODAY in types && MEETING in types -> futureMeetingColor
        SPECIAL in types && MEETING in types -> futureMeetingColor
        else -> null
    }

    val additionalIndicatorColor =
        if (TODAY in types && SPECIAL in types) specialColor else null

    val border =
        if (types.singleOrNull() == PAST) null else BorderStroke(1.dp, Color.Black)

    return AppDateUI(
        date = this.date,
        types = this.types,
        backgroundColor = backgroundColor,
        mainIndicatorColor = mainIndicatorColor,
        additionalIndicatorColor = additionalIndicatorColor,
        border = border
    )
}
