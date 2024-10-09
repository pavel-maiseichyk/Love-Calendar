package com.paulmais.lovecalendar.presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnDateClick -> {

            }
        }
    }
}