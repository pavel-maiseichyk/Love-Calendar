package com.paulmais.lovecalendar.auth.presentation

import com.paulmais.lovecalendar.core.presentation.UiText

interface AuthEvent {
    data object CompleteSign: AuthEvent
    data class ShowError(val error: UiText): AuthEvent
}