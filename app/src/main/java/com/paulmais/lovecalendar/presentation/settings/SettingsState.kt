package com.paulmais.lovecalendar.presentation.settings

import androidx.compose.foundation.text.input.TextFieldState

data class SettingsState(
    val specialDate: TextFieldState = TextFieldState(initialText = "")
)
