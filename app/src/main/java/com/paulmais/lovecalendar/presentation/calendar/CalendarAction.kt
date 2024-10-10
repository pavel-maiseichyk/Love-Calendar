package com.paulmais.lovecalendar.presentation.calendar

import com.paulmais.lovecalendar.domain.model.AppDate

sealed class CalendarAction {
    data object OnEditClick: CalendarAction()
    data object OnConfirmEditClick: CalendarAction()
    data object OnUndoEditClick: CalendarAction()
    data class OnDateTap(val appDate: AppDate): CalendarAction()
}