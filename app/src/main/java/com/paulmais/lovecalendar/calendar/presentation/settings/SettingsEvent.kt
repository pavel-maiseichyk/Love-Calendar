package com.paulmais.lovecalendar.calendar.presentation.settings

import com.paulmais.lovecalendar.core.presentation.UiText

interface SettingsEvent {
    data object NavigateBack : SettingsEvent
    data class ShowMessage(val uiText: UiText) : SettingsEvent
    data object Logout : SettingsEvent
}