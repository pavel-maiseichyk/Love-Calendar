package com.paulmais.lovecalendar.presentation.settings

interface SettingsEvent {
    data class ShowToast(val message: String) : SettingsEvent
}