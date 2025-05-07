package com.paulmais.lovecalendar.calendar.presentation.settings

interface SettingsAction {
    data object BackClicked : SettingsAction
    data object DateClicked : SettingsAction
    data class DateChanged(val text: String) : SettingsAction
    data object CancelUpdateDateClicked : SettingsAction
    data object UpdateDateClicked : SettingsAction
    data object LogoutClicked : SettingsAction
}