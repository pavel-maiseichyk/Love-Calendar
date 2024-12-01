package com.paulmais.lovecalendar.presentation.settings

interface SettingsAction {
    data object OnBackClick : SettingsAction
    data object OnDateClick : SettingsAction
    data object OnCancelUpdateDateClick : SettingsAction
    data object OnUpdateDateClick : SettingsAction
}