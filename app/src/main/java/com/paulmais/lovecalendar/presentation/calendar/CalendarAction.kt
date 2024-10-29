package com.paulmais.lovecalendar.presentation.calendar

sealed class CalendarAction {
    data object OnEditClick: CalendarAction()
    data object OnConfirmEditClick: CalendarAction()
    data object OnUndoEditClick: CalendarAction()
    data class OnDateTap(val appDate: AppDateUI): CalendarAction()
}