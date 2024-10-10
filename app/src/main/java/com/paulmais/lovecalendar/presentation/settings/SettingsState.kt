package com.paulmais.lovecalendar.presentation.settings

import androidx.compose.foundation.text.input.TextFieldState

data class SettingsState(
    val specialDateTextFieldState: TextFieldState = TextFieldState(initialText = ""),
    val specialDateString: String = "",
    val isEditingSpecialDate: Boolean = false
)
