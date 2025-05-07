package com.paulmais.lovecalendar.calendar.presentation.calendar

import com.paulmais.lovecalendar.calendar.presentation.calendar.models.AppDateUI
import kotlinx.datetime.LocalDate

sealed class CalendarAction {
    data object RefreshSyncClicked : CalendarAction()
    data object SettingsClicked : CalendarAction()
    data object EditClicked : CalendarAction()
    data object ConfirmEditClicked : CalendarAction()
    data object UndoEditClicked : CalendarAction()

    data object PreviousClicked : CalendarAction()
    data object NextClicked : CalendarAction()
    data class DateSelected(val appDate: AppDateUI) : CalendarAction()
    data class DaysUntilComponentClicked(val date: LocalDate) : CalendarAction()
}