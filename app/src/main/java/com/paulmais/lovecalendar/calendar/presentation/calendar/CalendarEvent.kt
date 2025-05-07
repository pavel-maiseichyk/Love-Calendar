package com.paulmais.lovecalendar.calendar.presentation.calendar

import com.paulmais.lovecalendar.core.presentation.UiText

sealed interface CalendarEvent {
    data class Error(val uiText: UiText): CalendarEvent
}